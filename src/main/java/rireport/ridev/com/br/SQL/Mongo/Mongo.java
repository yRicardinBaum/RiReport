package rireport.ridev.com.br.SQL.Mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.reactivestreams.client.*;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.reactivestreams.Publisher;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.User.User;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Mongo extends Backend {

    private MongoClient client;

    private MongoDatabase database;

    private MongoCollection<User> users;

    public Mongo(ConfigurationSection sec) {
        try {
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).register(User.class, Puniments.class).build()));

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(sec.getString("mongo")))
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            this.client = MongoClients.create(settings);

            this.database = this.client.getDatabase("RiReport");
            this.users = this.database.getCollection("User", User.class).withCodecRegistry(pojoCodecRegistry);

        } catch(MongoException e) {
            LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar conectar ao banco de dados: " + e.getMessage(), e);
        } finally {
            LOGGER.log(Level.INFO, "MongoDB carregada com sucesso!");
        }
    }

    @Override
    public User getUser(String playerName) {
        Publisher<User> pb = this.users.find(eq("username", playerName)).first();
        return Maybe.fromPublisher(pb).blockingGet();

    }

    @Override
    public User getUser(Player p) {
        Publisher<User> pb = this.users.find(eq("username", p.getName())).first();
        return Maybe.fromPublisher(pb).blockingGet();
    }


    @Override
    public void addReport(User us, Puniments pn) {
        us.getPuniments().add(pn);
        this.users.findOneAndReplace(eq("username", us.getUsername()), us).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }

    @Override
    public void removeReport(User us, Puniments pn) {
        us.getPuniments().remove(pn);
        this.users.findOneAndReplace(eq("username", us.getUsername()), us).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }


    @Override
    public void removeUser(String user) {
        this.users.deleteOne(eq("username", user)).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }

    @Override
    public void removeUser(Player user) {
        this.users.deleteOne(eq("username", user.getName())).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }

    @Override
    public void removeUser(User user) {
        this.users.deleteOne(eq("username", user.getUsername())).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }

    @Override
    public void insertUser(User user) {
        this.users.insertOne(user).subscribe(new SubscriberHelpers.OperationSubscriber<>());
    }

    @Override
    public List<User> getAllUsers() {
        FindPublisher<User> publisher = this.users.find();
        return Flowable.fromPublisher(publisher)
                .blockingStream()
                .collect(Collectors.toList());
    }


    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public MongoCollection<User> getUsers() {
        return users;
    }

    public void setUsers(MongoCollection<User> users) {
        this.users = users;
    }
}
