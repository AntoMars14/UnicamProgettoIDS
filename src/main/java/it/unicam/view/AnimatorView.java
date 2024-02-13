package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.model.util.ContentFD;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class AnimatorView implements UtenteView{

    private Controller controller;
    private Scanner in = new Scanner(System.in);
    private Desktop desktop = Desktop.getDesktop();

    public AnimatorView(Controller controller) {
        this.controller = controller;
    }

    public void createContest(){
        this.insertContestInfo();
        this.onInvite();
        System.out.println("Contest creato");
    }

    private void insertContestInfo(){
        System.out.println("Inserisci il nome del contest");
        String name = in.nextLine();
        System.out.println("Inserisci l'obiettivo del contest");
        String objective = in.nextLine();
        this.controller.insertContestInfo(name, objective);
    }

    private void onInvite(){
        System.out.println("Il contest è aperto a tutti? y/n");
        if(in.nextLine().equals("y")){
            this.controller.onInvite(false);
        } else{
            this.controller.onInvite(true);
        }
    }

    public void inviteContributors(){
        this.controller.getAllOpenedContestOnInvite().stream().forEach(contest -> System.out.println(contest.toString()));
        this.selectedContestContributors();
        String input = "y";
        while(input.equals("y")){
            System.out.println("Vuoi invitare un utente? y/n");
            input = in.nextLine();
            if(input.equals("y")){
                this.inviteContributor();
            }
        }

    }

    private void selectedContestContributors() {
        System.out.println("Inserisci l'id del contest a cui vuoi invitare gli utenti");
        this.controller.selectedContestContibutors(in.nextInt()).stream().forEach(utente -> System.out.println(utente.toString()));
        in.nextLine();
    }

    private void inviteContributor() {
        System.out.println("Inserisci l'id dell'utente che vuoi invitare");
        this.controller.inviteContributor(in.nextInt());
        in.nextLine();
        System.out.println("Utente invitato");
    }

    public void validateContestContent(){
        this.controller.getAllOpenedContest().stream().forEach(contest -> System.out.println(contest.toString()));
        this.viewPendingContentContest();
            this.selectedContestContent();
        System.out.println("Vuoi validare il contenuto? y/n");
        if(in.nextLine().equals("y")){
            this.validateContestC();
        }else {
            this.deleteContestContent();
        }
    }

    private void deleteContestContent() {
        this.controller.deleteContestContent();
        System.out.println("Contenuto eliminato");
    }

    private void validateContestC() {
        this.controller.validateContestC();
        System.out.println("Contenuto validato");
    }

    private void viewPendingContentContest() {
        System.out.println("Inserisci l'id del contest di cui vuoi validare il contenuto");
        this.controller.viewPendingContentContest(in.nextInt()).stream().forEach(content -> System.out.println(content.toString()));
        in.nextLine();
    }

    private void selectedContestContent() {
        System.out.println("Inserisci l'id del contest che vuoi validare");
        ContentFD content = this.controller.selectedContestContent(in.nextInt());
        in.nextLine();
        System.out.println(content.toString());
        try {
            desktop.open(content.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectContestWinner(){
        this.controller.getAllOpenedContest().stream().forEach(contest -> System.out.println(contest.toString()));
        this.selectedContestValidatedContent();
        this.selectedWinnerContent();
    }


    private void selectedContestValidatedContent() {
        System.out.println("Inserisci l'id del contest di cui vuoi selezionare il vincitore");
        this.controller.selectedContestValidatedContent(in.nextInt()).stream().forEach(content -> System.out.println(content.toString()));
        in.nextLine();
    }
    private void selectedWinnerContent() {
        System.out.println("Inserisci l'id del contenuto vincitore");
        this.controller.selectedWinnerContent(in.nextInt());
        in.nextLine();
        System.out.println("Vincitore selezionato e contest chiuso");
    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto animatore");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Crea Contest");
            System.out.println("2 - Invita contributori");
            System.out.println("3 - Valida contenuto contest");
            System.out.println("4 - Seleziona vincitore contest");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 0 -> exit = true;
                case 1 -> this.createContest();
                case 2 -> this.inviteContributors();
                case 3 -> this.validateContestContent();
                case 4 -> this.selectContestWinner();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}