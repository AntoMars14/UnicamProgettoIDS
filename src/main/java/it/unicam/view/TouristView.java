package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.model.utenti.Role;

public class TouristView extends ViewerView implements UtenteView{

    public TouristView(Controller controller) {
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
    public void viewContentContest() {
        super.viewContentContest();
    }

    public void registration(){
        if(this.registrationUser()) {
            System.out.println("Confermi l'invio della richiesta di registrazione? y/n");
            if (in.nextLine().equals("y")) {
                controller.confirmRegistration();
                System.out.println("Richiesta di registrazione inviata");
            } else {
                System.out.println("Richiesta di registrazione annullata");
            }
        }else{
            System.out.println("Email e/o username già presenti nel sistema");
        }
    }

    private boolean registrationUser() {
        System.out.println("Seleziona il ruolo che desideri avere");
        System.out.println("1 - TuristaAutenticato");
        System.out.println("2 - Contributor");
        Role role = null;
        switch (in.nextInt()){
            case 1 -> role = Role.TURISTAUTENTICATO;
            case 2 -> role = Role.CONTRIBUTOR;
            default -> {
                System.out.println("Errore nell'inserimento");
                this.registrationUser();
            }
        }
        in.nextLine();
        System.out.println("Inserisci la tua email");
        String email = in.nextLine();
        System.out.println("Inserisci il tuo username");
        String username = in.nextLine();
        System.out.println("Inserisci la tua password");
        String password = in.nextLine();
        return this.controller.registrationUser(email, username, password, role);

    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto turista");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Visualizza POI");
            System.out.println("2 - Visualizza Itinerario");
            System.out.println("3 - Visualizza Contenuto Contest");
            System.out.println("4 - Richiedi registrazione");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 0 -> exit = true;
                case 1 -> this.viewPoi();
                case 2 -> this.viewItinerary();
                case 3 -> this.viewContentContest();
                case 4 -> this.registration();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
