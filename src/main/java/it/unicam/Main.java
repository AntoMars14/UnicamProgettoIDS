package it.unicam;

import it.unicam.controller.Controller;
import it.unicam.model.Comune;
import it.unicam.model.Coordinates;
import it.unicam.view.AuthorizedContributorView;
import it.unicam.view.ContributorView;

public class Main {
    public static void main(String[] args) {
        Comune comune = new Comune("Camerino", new Coordinates(43.14255874, 13.078767));
        Controller appController = new Controller(comune);
        ContributorView cw = new ContributorView(appController);
        AuthorizedContributorView acw = new AuthorizedContributorView(appController);
        cw.viewPoi();
        acw.insertPOI();
        cw.insertPOI();
        acw.viewPoi();
        cw.createItinerary();
        acw.createItinerary();
    }
}