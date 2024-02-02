package it.unicam.view;

import it.unicam.controller.Controller;

import java.io.IOException;

public class CuratorView extends AuthorizedContributorView{

    public CuratorView(Controller controller) {
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
        super.confirmPOI();
    }

    public void validatePOI() {
        controller.getAllPendingPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.selectedPendingPOI();
        try {
            this.selectedContentPending();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        in.nextLine();
        System.out.println("Vuoi validare il POI? y/n");
        if (in.nextLine().equals("y")){
            this.validateSelectedPOI();
            System.out.println("Poi validato");
        }else {
            this.deletePendingPOI();
            System.out.println("POI eliminato");

        }
    }

    private void selectedPendingPOI(){
        System.out.println("Selezionare un poi da validare inserendo l'ID");
        System.out.println(controller.selectedPendingPOI(in.nextInt()).toString());
    }

    private void deletePendingPOI() {
        controller.deletePendingPOI();
    }

    private void validateSelectedPOI() {
        controller.validateSelectedPOI();
    }

    private void selectedContentPending() throws IOException {
        int input = -1;
        while (input != 0){
            System.out.println("Per visualizzare un contenuto inserire il suo id, per terminare di visualizzare contenuti inserire 0");
            input = in.nextInt();
            if(input>0) {
                desktop.open(controller.viewContentPending(input).getFile());
            }
        }
    }

    @Override
    public void createItinerary() {
        super.createItinerary();
    }

    @Override
    public void confirmCreationItinerary() {
        super.confirmCreationItinerary();
    }

    @Override
    public void addContent() {
        super.addContent();
    }

    @Override
    public void confirmAddContent() {
        super.confirmAddContent();
    }
}
