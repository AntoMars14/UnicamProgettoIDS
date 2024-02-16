package it.unicam.view;

import it.unicam.controller.Controller;

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


    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto Gestore della piattaforma");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Gestisci richieste di cambio ruolo");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 0 -> exit = true;
                case 1 -> this.manageChangeRole();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
