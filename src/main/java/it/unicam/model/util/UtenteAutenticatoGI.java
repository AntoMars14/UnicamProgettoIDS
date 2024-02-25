package it.unicam.model.util;

import it.unicam.model.utenti.Role;

public class UtenteAutenticatoGI {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;

    public UtenteAutenticatoGI(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public UtenteAutenticatoGI(){

    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "UtenteAutenticato id = " + id + " - Ruolo = "+this.role +
                "\nusername = " + username +
                " - email = " + email;
    }
}
