package rireport.ridev.com.br.SQL;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Libs.ModuleLogger;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Mongo.Mongo;
import rireport.ridev.com.br.SQL.SQL.MySQL.MySQL;
import rireport.ridev.com.br.SQL.SQL.SQLite.SQLite;
import rireport.ridev.com.br.User.User;


import java.util.List;
import java.util.logging.Level;

public abstract class Backend{
    public static final ModuleLogger LOGGER = new ModuleLogger("RiReport Backend");



    private static Backend backend;

    public abstract User getUser(String playerName);
    public abstract User getUser(Player p);

    public abstract void addReport(User us, Puniments pn);
    public abstract void removeReport(User us, Puniments pn);
    public abstract void removeUser(String user);
    public abstract void removeUser(Player user);
    public abstract void removeUser(User user);
    public abstract void insertUser(User user);
    public abstract List<User> getAllUsers();

    public static void loadBackend(ConfigurationSection sec) {
        switch(sec.getString("type").toLowerCase()) {
            case "mongodb": {
                LOGGER.log(Level.FINE, "Database selecionada: MongoDB. Conectando...");
                try {
                    setBackend( new Mongo(sec));
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar se conectar à database!", e);
                } finally {
                    LOGGER.log(Level.FINE, "Database conectada!");
                }
                break;
            }

            case "mysql": {
                LOGGER.log(Level.FINE, "Database selecionada: MySQL. Conectando...");
                try {
                    setBackend(new MySQL(sec));
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar se conectar à database!", e);
                } finally {
                    LOGGER.log(Level.FINE, "Database conectada!");
                }
                break;
            }

            case "sqlite":{
                LOGGER.log(Level.FINE, "Database selecionada: SQLite. Conectando...");
                try {
                    setBackend(new SQLite());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar se conectar à database!", e);
                } finally {
                    LOGGER.log(Level.FINE, "Database conectada!");
                }
                break;

            }

            default: {
                LOGGER.log(Level.FINE, "Não foi possivel encontrar a database selecionada, alternando para SQLite!");

                try {
                    setBackend(new SQLite());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Ocorreu um erro ao tentar se conectar à database!", e);
                } finally {
                    LOGGER.log(Level.FINE, "Database conectada!");
                }
            }
        }
    }


    public static Backend getBackend() {
        return backend;
    }

    public static void setBackend(Backend back) {
        backend = back;
    }
}
