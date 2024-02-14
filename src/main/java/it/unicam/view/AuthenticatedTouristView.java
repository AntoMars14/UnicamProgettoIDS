package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.view.io.FileChooser;

import java.io.File;
import java.util.Optional;

public class AuthenticatedTouristView  extends ViewerView implements UtenteView{

    private FileChooser fChooser = new FileChooser();
    private int id;

    public AuthenticatedTouristView(Controller controller, int id) {
        super(controller);
        this.id = id;
    }

    @Override
    public void viewPoi() {
        super.viewPoi();
    }

    @Override
    public void viewItinerary() {
        super.viewItinerary();
    }

    public void addPhoto(){
        this.controller.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.addContentToPOI();
        this.confirmAddContent();
    }

    private void addContentToPOI() {
        System.out.println("Seleziona l'id del POI al quale vuoi aggiungere una foto");
        int poiId = in.nextInt();
        in.nextLine();
        System.out.println("Inserisci il nome della foto");
        String name = in.nextLine();
        System.out.println("Inserisci la descrizione della foto");
        String desc = in.nextLine();
        File f;
        do{
            System.out.println("Inserisci il file della foto");
            f = fChooser.showFileChooser();
        }while(!getFileExtension(f.getName()).get().equals("png") && !getFileExtension(f.getName()).get().equals("jpg"));
        controller.addContentToPOI(poiId, name, desc, f);
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
    private void confirmAddContent() {
        System.out.println("Confermi l'inserimento della foto? y/n");
        if(in.nextLine().equals("y")){
            controller.confirmAddContentPending();
            System.out.println("Foto aggiunta in pending");
        }else{
            System.out.println("Aggiunta foto annullata");
        }
    }

    public void addToFavorites(){
        System.out.println("Seleziona l'oggetto da aggiungere ai preferiti");
        System.out.println("1 - POI");
        System.out.println("2 - Itinerario");
        int choice = in.nextInt();
        in.nextLine();
        switch(choice){
            case 1 -> {
                this.viewPOIs();
                this.addPOIToFavorites();

            }
            case 2 -> {
                this.viewItineraries();
                this.addToFavoritesItinerary();
            }
            default -> System.out.println("Errore nell'inserimento");
        }

    }

    private void viewItineraries() {
        this.controller.getAllItinerary().stream().forEach(i -> System.out.println(i.toString()));
    }

    private void addToFavoritesItinerary() {
        System.out.println("Inserisci l'id dell'itinerario da aggiungere ai preferiti");
        int itineraryId = in.nextInt();
        in.nextLine();
        if (!controller.addItineraryToFavorites(this.id, itineraryId)){
            System.out.println("Itinerario già presente nei preferiti");
        }else{
            System.out.println("Itinerario aggiunto ai preferiti");
        }
    }

    private void addPOIToFavorites() {
        System.out.println("Inserisci l'id del POI da aggiungere ai preferiti");
        int POIid = in.nextInt();
        in.nextLine();
        if (!controller.addPOIToFavorites(this.id, POIid)){
            System.out.println("POI già presente nei preferiti");
        }else{
            System.out.println("POI aggiunto ai preferiti");
        }
    }

    private void viewPOIs() {
        this.controller.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
    }


    public void viewFavorites(){
        System.out.println("Cosa vuoi visualizzare?");
        System.out.println("1 - POI preferiti");
        System.out.println("2 - Itinerari preferiti");
        int choice = in.nextInt();
        in.nextLine();
        switch(choice){
            case 1 -> this.viewFavoritesPOIs();
            case 2 -> this.viewFavoritesItineraries();
            default -> System.out.println("Errore nell'inserimento");
        }
    }

    private void viewFavoritesPOIs() {
        this.controller.viewFavoritesPOIs(this.id).stream().forEach(p -> System.out.println(p.toString()));
    }

    private void viewFavoritesItineraries() {
        this.controller.viewFavoritesItineraries(this.id).stream().forEach(i -> System.out.println(i.toString()));
    }


    @Override
    public void viewContentContest() {
        super.viewContentContest();
    }

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto turista autenticato");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Visualizza POI");
            System.out.println("2 - Visualizza Itinerario");
            System.out.println("3 - Aggiungi foto");
            System.out.println("4 - Aggiungi ai preferiti");
            System.out.println("5 - Visualizza preferiti");
            System.out.println("6 - Visualizza Contenuto Contest");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch(choice){
                case 0 -> exit = true;
                case 1 -> this.viewPoi();
                case 2 -> this.viewItinerary();
                case 3 -> this.addPhoto();
                case 4 -> this.addToFavorites();
                case 5 -> this.viewFavorites();
                case 6 -> this.viewContentContest();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
