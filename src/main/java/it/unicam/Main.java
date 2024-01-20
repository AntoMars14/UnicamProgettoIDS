package it.unicam;

import it.unicam.model.Comune;
import it.unicam.model.Coordinates;
import it.unicam.view.UserInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Comune comune = new Comune("Camerino", new Coordinates(43.14255874, 13.078767));
        UserInterface u = new UserInterface(comune);
        try {
            u.viewPOI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        u.insertPOI();
        u.confirmPOI();
        u.insertPOI();
        u.confirmPOIPending();
        try {
            u.viewPOI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}