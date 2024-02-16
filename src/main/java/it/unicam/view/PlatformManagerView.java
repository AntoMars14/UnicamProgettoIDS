package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.model.utenti.Role;

import java.util.Scanner;

public class PlatformManagerView implements UtenteView{

    private Controller controller;
    private Scanner in = new Scanner(System.in);


    public PlatformManagerView(Controller controller) {
        this.controller = controller;
    }
    public void manageChangeRole(){
        this.controller.viewChangeRoleRequests().stream().forEach(r -> System.out.println(r.toString()));
        this.selectRequestUser();
    }

    private void selectRequestUser() {
        System.out.println("Seleziona l'id dell'utente a cui vuoi gestire la richiesta di cambio ruolo");
        int id = in.nextInt();
        in.nextLine();
        System.out.println("Vuoi approvare la richiesta di cambio ruolo al ruolo successivo? y/n");
        if (in.nextLine().equals("y")) {
            this.approveRequest(id);
        }else {
            this.disapproveRequest(id);
        }
    }

    private void disapproveRequest(int id) {
        this.controller.disapproveRequest(id);
        System.out.println("Richiesta di cambio ruolo non approvata");
    }

    private void approveRequest(int id) {
        this.controller.approveRequest(id);
        System.out.println("Richiesta di cambio ruolo approvata");
    }

    public void managePrivileges(){
        this.controller.viewAllUsers().stream().forEach(u -> System.out.println(u.toString()));
        this.changeRole();

    }

    private void changeRole() {
        System.out.println("Seleziona l'id dell'utente a cui vuoi cambiare il ruolo");
        int id = in.nextInt();
        in.nextLine();
        System.out.println("Seleziona il ruolo che vuoi assegnare all'utente");
        System.out.println("1 - TuristaAutenticato");
        System.out.println("2 - Contributor");
        System.out.println("3 - ContributorAutorizzato");
        System.out.println("4 - Curatore");
        System.out.println("5 - Animatore");
        int choice = in.nextInt();
        in.nextLine();
        switch (choice) {
            case 1 -> this.controller.changeRole(id, Role.TURISTAUTENTICATO);
            case 2 -> this.controller.changeRole(id,Role.CONTRIBUTOR);
            case 3 -> this.controller.changeRole(id, Role.CONTRIBUTORAUTORIZZATO);
            case 4 -> this.controller.changeRole(id, Role.CURATORE);
            case 5 -> this.controller.changeRole(id, Role.ANIMATORE);
            default -> System.out.println("Errore nell'inserimento");
        }
    }

    public void approveRegistration(){
        this.controller.viewRegistrationUsers().stream().forEach(r -> System.out.println(r.toString()));
        this.selectedRegistrationUser();
        System.out.println("Vuoi approvare la registrazione? y/n");
        if (in.nextLine().equals("y")) {
            this.approveRegistrationR();
        }else {
            this.refuseRegistrationR();
        }
    }

    private void selectedRegistrationUser() {
        System.out.println("Seleziona l'id dell'utente a cui vuoi esaminare la registrazione");
        this.controller.selectedRegistrationUser(in.nextInt());
        in.nextLine();
    }

    private void refuseRegistrationR() {
        this.controller.refuseRegistration();
        System.out.println("Registrazione rifiutata");
    }

    private void approveRegistrationR() {
        this.controller.approveRegistration();
        System.out.println("Registrazione approvata");
    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto Gestore della piattaforma");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Gestisci richieste di cambio ruolo");
            System.out.println("2 - Gestisci privilegi");
            System.out.println("3 - Approva registrazione");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 0 -> exit = true;
                case 1 -> this.manageChangeRole();
                case 2 -> this.managePrivileges();
                case 3 -> this.approveRegistration();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
