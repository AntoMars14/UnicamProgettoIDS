package it.unicam.model.utenti;

import it.unicam.controllersRest.ComuneController;
import it.unicam.model.util.UtenteAutenticatoGI;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UtenteAutenticato implements Utente{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_generator")
    private Long id;
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

    public UtenteAutenticato() {

    }


    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Role getRole() {
        return role;
    }


    public UtenteAutenticatoGI getGeneralInfoUtenteAutenticato() {
        return new UtenteAutenticatoGI(this.id, this.username, this.email, this.password, this.role);
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
