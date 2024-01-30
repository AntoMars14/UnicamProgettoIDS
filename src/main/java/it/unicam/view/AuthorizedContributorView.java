package it.unicam.view;

import it.unicam.controller.Controller;

public class AuthorizedContributorView extends ContributeView {

    public AuthorizedContributorView(Controller controller) {
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
            controller.confirmPoi();
            System.out.println("Poi inserito sulla piattaforma");
        }else{
            System.out.println("Annullamento inserimento Poi sulla piattaforma");
        }
    }
}
