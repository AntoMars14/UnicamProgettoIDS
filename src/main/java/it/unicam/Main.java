package it.unicam;

import it.unicam.controller.Controller;
import it.unicam.model.Comune;
import it.unicam.model.ContestManager;
import it.unicam.model.Coordinates;
import it.unicam.model.controllersGRASP.TimeController;
import it.unicam.model.util.tasks.DeleteExpiredItineraries;
import it.unicam.model.util.tasks.DeleteExpiredPOIs;
import it.unicam.view.*;

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
        Runnable deleteExpiredPOIs = new DeleteExpiredPOIs(comune);
        Runnable deleteExpiredItineraries = new DeleteExpiredItineraries(comune);
        TimeController timeController = new TimeController();
        timeController.scheduleMidnightTask(deleteExpiredPOIs);
        timeController.scheduleMidnightTask(deleteExpiredItineraries);
        cw.viewPoi();
        acw.insertPOI();
        acw.insertPOI();
        acw.createItinerary();
        acw.viewPoi();
        acw.viewPoi();
        acw.viewPoi();
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