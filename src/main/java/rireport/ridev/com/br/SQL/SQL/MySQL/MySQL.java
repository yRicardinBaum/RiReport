package rireport.ridev.com.br.SQL.SQL.MySQL;

import com.google.gson.Gson;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import rireport.ridev.com.br.Puniments.Puniments;
import rireport.ridev.com.br.SQL.Backend;
import rireport.ridev.com.br.SQL.SQL.UserSQL;
import rireport.ridev.com.br.User.User;

import java.util.ArrayList;
import java.util.List;


public class MySQL extends Backend {
    private static MySQLDatabaseType mysql;

    private static SQLExecutor executor;
    private static ConfigurationSection sec;
    public MySQL(ConfigurationSection sec) {
        MySQL.sec = sec;
        mysql = MySQLDatabaseType.builder()
                .address(sec.getString("address") + ":3306")
                .username(sec.getString("username"))
                .password(sec.getString("password"))
                .database(sec.getString("database"))
                .build();
        executor = new SQLExecutor(mysql.connect());

        getExecutor().updateQuery(
                "CREATE TABLE IF NOT EXISTS" + sec.getString("mysql-table")+ " ("
                        + "username VARCHAR NOT NULL PRIMARY KEY,"
                        + "puniments TEXT NOT NULL"
                        + ");"
        );
    }

    @Override
    public User getUser(String playerName) {
        return getExecutor().resultOneQuery(
                "SELECT * FROM " + sec.getString("mysql-table") + " WHERE username = ?",
                statement -> statement.set(1, playerName),
                UserSQL.class);
    }

    @Override
    public User getUser(Player p) {
        return getExecutor().resultOneQuery(
                "SELECT * FROM " + sec.getString("mysql-table")+ " WHERE username = ?",
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
        getExecutor().updateQuery("DELETE FROM " + sec.getString("mysql-table")+ " WHERE username = " + user);
    }

    @Override
    public void removeUser(Player user) {
        getExecutor().updateQuery("DELETE FROM " + sec.getString("mysql-table")+ " WHERE username = " + user.getName());
    }

    @Override
    public void removeUser(User user) {
        getExecutor().updateQuery("DELETE FROM " + sec.getString("mysql-table")+ " WHERE username = " + user.getUsername());
    }

    @Override
    public void insertUser(User user) {
        getExecutor().updateQuery(
                "REPLACE INTO " + sec.getString("mysql-table")+ " VALUES(?, ?)",
                statement -> {
                    statement.set(1, user.getUsername());
                    statement.set(2, new Gson().toJson(user.getPuniments()));
                }
        );
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(getExecutor().resultManyQuery(
                "SELECT * FROM " + sec.getString("mysql-table"),
                statement -> {
                },
                UserSQL.class));
    }

    public static MySQLDatabaseType getMysql() {
        return mysql;
    }

    public static SQLExecutor getExecutor() {
        return executor;
    }



}
