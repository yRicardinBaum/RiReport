package rireport.ridev.com.br.Puniments;

public class Puniments {
    private String author;
    private String reason;

    public Puniments(String author, String reason) {
        this.author = author;
        this.reason = reason;
    }

    public Puniments() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
