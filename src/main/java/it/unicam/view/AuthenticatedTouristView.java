package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.view.io.FileChooser;

import java.io.File;
import java.util.Optional;

public class AuthenticatedTouristView  extends ViewerView implements UtenteView{

    FileChooser fChooser = new FileChooser();
    public AuthenticatedTouristView(Controller controller) {
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

    @Override
    public void getView() {
        boolean exit = false;
        while(!exit){
            System.out.println("Benvenuto nella vista turista autenticato");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Visualizza POI");
            System.out.println("2 - Visualizza Itinerario");
            System.out.println("3 - Aggiungi foto");
            System.out.println("0 - Esci");
            int choice = in.nextInt();
            in.nextLine();
            switch(choice){
                case 0 -> exit = true;
                case 1 -> this.viewPoi();
                case 2 -> this.viewItinerary();
                case 3 -> this.addPhoto();
                default -> System.out.println("Errore nell'inserimento");
            }
        }
    }
}
