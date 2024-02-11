package it.unicam.model.util;

public class UtenteAutenticatoGI {

    private final int id;
    private final String username;
    private final String email;
    private final String password;

    public UtenteAutenticatoGI(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UtenteAutenticato id = " + id +
                "\nusername = " + username +
                " - email = " + email;
    }
}
