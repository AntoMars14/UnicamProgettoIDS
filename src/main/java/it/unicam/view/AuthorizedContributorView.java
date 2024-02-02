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

    @Override
    public void createItinerary() {
        super.createItinerary();
    }

    @Override
    public void confirmCreationItinerary() {

        System.out.println("confermi l'inserimento dell'itinerario? y/n");
        if (in.nextLine().equals("y")){
            controller.confirmCreationItinerary();
            System.out.println("itinerario inserito nella piattaforma");
        }else{
            System.out.println("creazione itinerario annullata");
        }
    }

    @Override
    public void addContent() {
        super.addContent();
    }

    @Override
    public void confirmAddContent() {
        System.out.println("Confermi l'inserimento del contenuto? y/n");
        if(in.nextLine().equals("y")){
            controller.confirmAddContent();
            System.out.println("Contenuto aggiunto");
        }else{
            System.out.println("Aggiunta contenuto annullata");
        }

    }
}