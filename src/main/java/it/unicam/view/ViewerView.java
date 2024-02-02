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
            this.selectedContents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void selectedPOI(){
        System.out.println("Seleziona l'id del POI che vuoi visualizzare");
        int poiId = in.nextInt();
        System.out.println(controller.viewSelectedPOI(poiId).toString());
    }

    protected void selectedContents() throws IOException {
        int input = -1;
        while (input != 0){
            System.out.println("Per visualizzare un contenuto inserire il suo id, per terminare di visualizzare contenuti inserire 0");
            input = in.nextInt();
            this.selectedContent(input);
        }
    }

    protected void selectedContent(int POIId) throws IOException {
        if(POIId>0) {
            desktop.open(controller.viewContent(POIId).getFile());
        }
    }

    public void viewItinerary() {
        controller.getAllItinerary().stream().forEach(i -> System.out.println(i.toString()));
        this.selectedItinerary();
    }

    private void selectedItinerary(){
        System.out.println("Selezionare l'itinerario da visualizzare inserendo l'ID");
        System.out.println(controller.selectedItinerary(in.nextInt()).toString());
    }




}
