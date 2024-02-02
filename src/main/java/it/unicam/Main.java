package it.unicam;

import it.unicam.controller.Controller;
import it.unicam.model.Comune;
import it.unicam.model.Coordinates;
import it.unicam.view.AuthenticatedTouristView;
import it.unicam.view.AuthorizedContributorView;
import it.unicam.view.ContributorView;
import it.unicam.view.CuratorView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Comune comune = new Comune("Camerino", new Coordinates(43.14255874, 13.078767));
        Controller appController = new Controller(comune);
        CuratorView cur = new CuratorView(appController);
        ContributorView cw = new ContributorView(appController);
        AuthorizedContributorView acw = new AuthorizedContributorView(appController);
        AuthenticatedTouristView atw = new AuthenticatedTouristView(appController);
        cw.viewPoi();
        acw.insertPOI();
        cw.insertPOI();
        cw.insertPOI();
        cur.validatePOI();
        cur.validatePOI();
        acw.viewPoi();
        cw.createItinerary();
        acw.createItinerary();
        acw.viewItinerary();
        cw.addContent();
        acw.addContent();
        acw.addContent();
        atw.addPhoto();
        atw.addPhoto();
        acw.viewPoi();
        cur.deleteObject();
        cur.deleteObject();
    }
}