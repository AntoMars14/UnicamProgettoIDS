package it.unicam.model.utenti;

import it.unicam.controller.Controller;
import it.unicam.view.TouristView;

public class Turista implements Utente{
    @Override
    public void utenteView(Controller controller) {
        new TouristView(controller).getView();
    }
}
