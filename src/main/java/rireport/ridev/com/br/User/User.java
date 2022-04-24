package rireport.ridev.com.br.User;

import rireport.ridev.com.br.Puniments.Puniments;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<Puniments> puniments;

    public User(String username, ArrayList<Puniments> puniments) {
        this.username = username;
        this.puniments = puniments;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Puniments> getPuniments() {
        return puniments;
    }

    public void setPuniments(ArrayList<Puniments> puniments) {
        this.puniments = puniments;
    }
}
