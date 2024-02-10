package it.unicam.view;

import it.unicam.controller.Controller;

import java.util.Scanner;

public class AnimatorView {

    private Controller controller;
    private Scanner in = new Scanner(System.in);

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
}
