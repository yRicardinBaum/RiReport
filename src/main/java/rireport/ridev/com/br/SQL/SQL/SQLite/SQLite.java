package rireport.ridev.com.br.SQL.SQL.SQLite;

import com.google.gson.GsonBuilder;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.SQL.SQL.UserSQL;
import rireport.ridev.com.br.User.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends Backend {
    private static SQLiteDatabaseType sqlite;

    private static SQLExecutor executor;

    public SQLite() {
        sqlite =  SQLiteDatabaseType.builder()
                .file(new File("plugins/RiReport/sql/database.db"))
                .build();
        executor = new SQLExecutor(sqlite.connect());

        getExecutor().updateQuery(
                "CREATE TABLE IF NOT EXISTS users ("
                        + "username VARCHAR NOT NULL PRIMARY KEY,"
                        + "puniments TEXT NOT NULL"
                        + ");"
        );
    }
    @Override
    public User getUser(String playerName) {
        return getExecutor().resultOneQuery(
                "SELECT * FROM users WHERE username = ?",
                statement -> statement.set(1, playerName),
                UserSQL.class);
    }

    @Override
    public User getUser(Player p) {
        return getExecutor().resultOneQuery(
                "SELECT * FROM users WHERE username = ?",
                statement -> statement.set(1, p.getName()),
                UserSQL.class);
    }

    @Override
    public void addReport(User us, Puniments pn) {
        us.getPuniments().add(pn);
        insertUser(us);
    }

    @Override
    public void removeReport(User us, Puniments pn) {
        us.getPuniments().remove(pn);
        insertUser(us);
    }


    @Override
    public void removeUser(String user) {
        getExecutor().updateQuery("DELETE FROM users WHERE username = " + user);
    }

    @Override
    public void removeUser(Player user) {
        getExecutor().updateQuery("DELETE FROM users WHERE username = " + user.getName());
    }

    @Override
    public void removeUser(User user) {
        getExecutor().updateQuery("DELETE FROM users WHERE username = " + user.getUsername());
    }

    @Override
    public void insertUser(User user) {
        getExecutor().updateQuery(
                "REPLACE INTO users VALUES(?, ?)",
                statement -> {
                    statement.set(1, user.getUsername());
                    statement.set(2, new GsonBuilder().setPrettyPrinting().create().toJson(user.getPuniments()));
                }
        );
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(getExecutor().resultManyQuery(
                "SELECT * FROM users",
                statement -> {
                },
                UserSQL.class));
    }

    public static SQLiteDatabaseType getSqlite() {
        return sqlite;
    }

    public static void setSqlite(SQLiteDatabaseType sqlite) {
        SQLite.sqlite = sqlite;
    }

    public static SQLExecutor getExecutor() {
        return executor;
    }

    public static void setExecutor(SQLExecutor executor) {
        SQLite.executor = executor;
    }
}
