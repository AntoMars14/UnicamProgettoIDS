package it.unicam.model.utenti;

import it.unicam.controller.Controller;

public class Turista implements Utente{
    @Override
    public void utenteView(Controller controller) {
       // new TouristView(controller).getView();
    }
}
