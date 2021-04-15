package model;

public class Angajat {
    private int id;
    private String username;
    private String pass;


    public Angajat(int id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public Angajat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}