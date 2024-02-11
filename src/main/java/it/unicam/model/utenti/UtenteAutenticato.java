package it.unicam.model.utenti;

import it.unicam.model.util.UtenteAutenticatoGI;

public class UtenteAutenticato implements Utente{

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;

    public UtenteAutenticato(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public void utenteView() {

    }

    public UtenteAutenticatoGI getGeneralInfoUtenteAutenticato() {
        return new UtenteAutenticatoGI(this.id, this.username, this.email, this.password);
    }
}
