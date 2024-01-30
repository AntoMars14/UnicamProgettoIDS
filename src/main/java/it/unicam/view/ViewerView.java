package it.unicam.view;

import it.unicam.controller.Controller;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public abstract class ViewerView {

    protected Controller controller;
    protected Scanner in = new Scanner(System.in);

    protected Desktop desktop = Desktop.getDesktop();

    public ViewerView(Controller controller) {
        this.controller = controller;
    }

    public void viewPoi() {
        controller.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.selectedPOI();
        try {
            this.selectedContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectedPOI(){
        System.out.println("Seleziona l'id del POI che vuoi visualizzare");
        int poiId = in.nextInt();
        System.out.println(controller.viewSelectedPOI(poiId).toString());
    }

    private void selectedContent() throws IOException {
        int input = -1;
        while (input != 0){
            System.out.println("Per visualizzare un contenuto inserire il suo id, per terminare di visualizzare contenuti inserire 0");
            input = in.nextInt();
            if(input>0) {
                desktop.open(controller.viewContent(input).getFile());
            }
        }
    }

    public void viewItinerary() {

    }


}
