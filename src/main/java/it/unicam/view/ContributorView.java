package it.unicam.view;

import it.unicam.controller.Controller;

public class ContributorView extends ContributeView implements UtenteView{


    public ContributorView(Controller controller, int id) {
        super(controller, id);
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

    @Override
    public void createItinerary() {
        super.createItinerary();
    }

    @Override
    public void confirmCreationItinerary() {

        System.out.println("confermi l'inserimento dell'itinerario? y/n");
        if (in.nextLine().equals("y")){
            controller.confirmCreationPendingItinerary();
            System.out.println("itinerario inserito in pending nella piattaforma");
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
            controller.confirmAddContentPending();
            System.out.println("Contenuto  in pending");
        }else{
            System.out.println("Aggiunta contenuto annullata");
        }
    }

    @Override
    public void partecipateToContest(){
        super.partecipateToContest();
    }

    @Override
    public void viewContentContest() {
        super.viewContentContest();
    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto contributor");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Visualizza POI");
            System.out.println("2 - Visualizza Itinerario");
            System.out.println("3 - Inserisci POI");
            System.out.println("4 - Crea Itinerario");
            System.out.println("5 - Aggiungi Contenuto");
            System.out.println("6 - Partecipa a Contest");
            System.out.println("7 - Visualizza Contenuto Contest");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 0 -> exit = true;
                case 1 -> this.viewPoi();
                case 2 -> this.viewItinerary();
                case 3 -> this.insertPOI();
                case 4 -> this.createItinerary();
                case 5 -> this.addContent();
                case 6 -> this.partecipateToContest();
                case 7 -> this.viewContentContest();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
