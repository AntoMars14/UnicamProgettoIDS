package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.POIFD;

import java.io.IOException;
import java.util.stream.Collectors;

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

    public void deleteObject(){
        System.out.println("Cosa vuoi eliminare?");
        System.out.println("1) POI\n2) Contenuto\n3) Itinerario");
        int input = in.nextInt();
        in.nextLine();
        switch(input){
            case 1 -> {
                this.viewPoi();
                in.nextLine();
                System.out.println("Vuoi eliminare il POI? y/n");
                if (in.nextLine().equals("y")){
                    this.deletePOI();
                    System.out.println("Eliminazione andata a buon fine");
                }else {
                    System.out.println("Eliminazione POI annullata");
                }
            }
            case 2 ->{ 
                this.viewContent();
                System.out.println("Vuoi eliminare il Contenuto? y/n");
                if (in.nextLine().equals("y")){
                    this.deleteContent();
                    System.out.println("Eliminazione andata a buon fine");
                }else {
                    System.out.println("Eliminazione Contenuto annullata");
                }
            }
            case 3 ->{ 
                this.viewItinerary();
                in.nextLine();
                System.out.println("Vuoi eliminare l'Itinerario? y/n");
                if (in.nextLine().equals("y")){
                    this.deleteItinerary();
                    System.out.println("Eliminazione andata a buon fine");
                }else {
                    System.out.println("Eliminazione Itinerario annullata");
                }
            }
            default -> System.out.println("Errore nell'input");
        }


    }

    private void deleteItinerary() {
        this.controller.deleteItinerary();
    }

    private void deleteContent() {
        this.controller.deleteContent();
    }

    private void deletePOI() {
        this.controller.deletePOI();
    }

    private void viewContent() {
        this.controller.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.selectedPOI();
        System.out.println("Inserisci l'id del contenuto che vuoi eliminare");
        try {
            this.selectedContent(in.nextInt());
            in.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateContent(){
        controller.getAllPendingContentPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.selectPendingContentPOI();
        this.selectedPendingContent();
        System.out.println("Vuoi validare il contenuto? y/n");
        if(in.nextLine().equals("y")){
            this.validateSelectedContent();
        }else{
            this.deletePendingContent();
        }
    }

    private void selectPendingContentPOI() {
        System.out.println("Seleziona il POI per il quale vuoi validare il contenuto in pending associato");
        POIFD p = controller.viewSelectedPOI(in.nextInt());
        in.nextLine();
        System.out.println(p.toString());
        System.out.println("Contenuti in pending:");
        System.out.println(p.getPendingContentsGI().stream().map(pc -> pc.toString()).collect(Collectors.joining("\n")));
    }

    private void selectedPendingContent() {
        System.out.println("Seleziona l'id del contenuto in pending che vuoi validare");
        ContentFD c = controller.selectedPendingContent(in.nextInt());
        in.nextLine();
        System.out.println(c.toString());
        try {
            desktop.open(c.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePendingContent() {
        controller.deletePendingContent();
        System.out.println("Contenuto eliminato dalla piattaforma");
    }

    private void validateSelectedContent() {
        controller.validateSelectedContent();
        System.out.println("Contenuto validato");
    }


}
