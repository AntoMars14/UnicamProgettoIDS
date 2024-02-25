package it.unicam.model.utenti;

import it.unicam.controllersRest.ComuneController;

public interface Utente {

    public Role getRole();
    public String getUsername();
    public String getPassword();
}
