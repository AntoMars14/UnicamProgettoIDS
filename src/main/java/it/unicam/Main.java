package it.unicam;

import it.unicam.controller.Controller;
import it.unicam.model.Comune;
import it.unicam.model.ContestManager;
import it.unicam.model.Coordinates;
import it.unicam.view.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Comune comune = new Comune("Camerino", new Coordinates(43.14255874, 13.078767));
        ContestManager cm = new ContestManager();
        Controller appController = new Controller(comune, cm);
        CuratorView cur = new CuratorView(appController);
        ContributorView cw = new ContributorView(appController);
        AuthorizedContributorView acw = new AuthorizedContributorView(appController);
        AuthenticatedTouristView atw = new AuthenticatedTouristView(appController);
        AnimatorView anw = new AnimatorView(appController);
        cw.viewPoi();
        acw.insertPOI();
        cw.insertPOI();
        cw.insertPOI();
        cur.validatePOI();
        cur.validatePOI();
        acw.viewPoi();
        cw.createItinerary();
        cw.createItinerary();
        acw.createItinerary();
        cur.validateItinerary();
        cur.validateItinerary();
        acw.viewItinerary();
        cw.addContent();
        acw.addContent();
        acw.addContent();
        atw.addPhoto();
        atw.addPhoto();
        acw.viewPoi();
        cur.validateContent();
        cur.validateContent();
        cur.deleteObject();
        acw.viewItinerary();
        cur.deleteObject();
        anw.createContest();

    }
}