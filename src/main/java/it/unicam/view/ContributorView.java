package it.unicam.view;

import it.unicam.controller.Controller;

public class ContributorView extends ContributeView {


    public ContributorView(Controller controller) {
        super(controller);
    }

    @Override
    public void viewPoi() {
        super.viewPoi();
    }

    @Override
    public void viewItinerary() {
        super.viewItinerary();
    }

    @Override
    public void insertPOI() {
        super.insertPOI();
    }

    @Override
    public void confirmPOI() {
        System.out.println("Confermi l'inserimento del POI? y/n");
        if(in.nextLine().equals("y")){
            controller.confirmPoiPending();
            System.out.println("Poi inserito sulla piattaforma in pending");
        }else{
            System.out.println("Annullamento inserimento Poi in pending sulla piattaforma");
        }
    }
}
