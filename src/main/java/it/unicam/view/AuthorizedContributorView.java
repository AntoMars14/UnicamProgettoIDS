package it.unicam.view;

import it.unicam.controller.Controller;

public class AuthorizedContributorView extends ContributeView implements UtenteView{

    public AuthorizedContributorView(Controller controller, int id) {
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

    @Override
    public void partecipateToContest(){
        super.partecipateToContest();
    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto contributore autorizzato");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Visualizza POI");
            System.out.println("2 - Visualizza Itinerario");
            System.out.println("3 - Inserisci POI");
            System.out.println("4 - Crea Itinerario");
            System.out.println("5 - Aggiungi Contenuto");
            System.out.println("6 - Partecipa a Contest");
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
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
