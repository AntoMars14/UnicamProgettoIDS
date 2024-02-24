package it.unicam.model.util;

import it.unicam.model.utenti.Role;

public class UtenteAutenticatoGI {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    private final Role role;

    public UtenteAutenticatoGI(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UtenteAutenticato id = " + id + " - Ruolo = "+this.role +
                "\nusername = " + username +
                " - email = " + email;
    }
}
