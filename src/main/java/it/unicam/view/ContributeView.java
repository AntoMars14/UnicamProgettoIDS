package it.unicam.view;

import it.unicam.controller.Controller;
import it.unicam.model.POIEventoFactory;
import it.unicam.model.POILuogoConOraFactory;
import it.unicam.model.POILuogoFactory;
import it.unicam.model.Type;
import it.unicam.view.io.FileChooser;
import it.unicam.view.io.MapHandler;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class ContributeView extends ViewerView implements IContributorsView{

    protected FileChooser fChooser;
    protected int contributorId;

    public ContributeView(Controller controller, int contributorId) {
        super(controller);
        this.fChooser = new FileChooser();
        this.contributorId = contributorId;
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

        System.out.println("Seleziona le coordinate sulla mappa del punto di interesse da inserire\n");
        MapHandler map = controller.map();
        this.selectPoint(map.showMap());
        Type t = null;
        do{
            System.out.println("Seleziona il tipo di POI che vuoi inserire: 1 Luogo - 2 Luogo con ora - 3 Evento");
            switch (in.nextInt()) {
                case 1 -> t = Type.LUOGO;
                case 2 -> t = Type.LUOGOCONORA;
                case 3 -> t = Type.EVENTO;
                default -> System.out.println("Errore nell'inserimento");
            }
        }while(t==null);
        this.selectType(t);
        in.nextLine();
        this.insertPOIInfo();
        if(t.equals(Type.LUOGOCONORA)){
            this.insertTime();
        }
        if(t.equals(Type.EVENTO)){
            this.insertDate();
        }
        String exit = "";
        while(!exit.equals("n")){
            System.out.println("Vuoi inserire un contenuto? y/n");
            exit = in.nextLine();
            if(!exit.equals("n")){
                this.insertContent();
            }
        }
        this.confirmPOI();
    }

    private void selectPoint(ICoordinate c){
        if(!controller.selectPoint(c)){
            System.out.println("Coordinate selezionate non valide, seleziona coordinate nel comune e a cui non è già associato un POI");
            this.insertPOI();
        };
    }
    private void insertPOIInfo(){
        System.out.println("Inserisci il nome del POI");
        String name = in.nextLine();
        System.out.println("Inserisci la descrizione del POI");
        String desc = in.nextLine();
        controller.insertPoiInfo(name, desc);
    }

    private void selectType(Type t){
        switch (t){
            case Type.LUOGO -> controller.selectType(new POILuogoFactory());
            case Type.LUOGOCONORA -> controller.selectType(new POILuogoConOraFactory());
            case Type.EVENTO -> controller.selectType(new POIEventoFactory());
        }
    }

    private void insertTime(){
        LocalTime[] opent = new LocalTime[7];
        LocalTime[] closet = new LocalTime[7];
        System.out.println("Inserisci orario di apertura e chiusura(a partire da Lunedì)");
        for (int i = 0; i < 6; i++){
            System.out.println("Inserisci ora di apertura nel formato HH:mm");
            opent[i] = LocalTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
            System.out.println("Inserisci ora di chiusura nel formato HH:mm");
            closet[i] = LocalTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
        }
        controller.insertTime(opent, closet);
    }

    private void insertDate(){
        System.out.println("Inserire la data di apertura nel formato gg-mm-yyyy hh:mm");
        LocalDateTime opend = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        System.out.println("Inserire la data di chiusura nel formato gg-mm-yyyy hh:mm");
        LocalDateTime closed = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        controller.insertDate(opend, closed);
    }
    private void insertContent() {
        System.out.println("Inserisci il nome del contenuto");
        String n = in.nextLine();
        System.out.println("Inserisci la descrizione del contenuto");
        String d = in.nextLine();
        System.out.println("Inserisci il file del contenuto");
        File f = fChooser.showFileChooser();
        controller.insertContent(n, d, f);
    }
    public abstract void confirmPOI();

    @Override
    public void createItinerary() {
        this.insertItineraryInfo();
        controller.getAllPOI().stream().forEach(poigi -> System.out.println(poigi.toString()));
        this.addPOI();
        System.out.println("L'itinerario ha una validità? y/n");
        if (in.nextLine().equals("y")){
            this.insertItineraryValidity();
        }
        this.confirmCreationItinerary();
    }
    private void insertItineraryInfo(){
        System.out.println("Inserire nome dell'itinerario");
        String name = in.nextLine();
        System.out.println("Inserire la descrizione dell'itinerario");
        String description = in.nextLine();
        controller.insertItineraryInfo(name, description);
    }

    private void addPOI(){
        int c = 0;
        String input = "";
        do {
            System.out.println("Inserisci id del POI che vuoi aggiungere all'itinerario");
            controller.addPOI(in.nextInt());
            in.nextLine();
            c++;
            if (c >= 2){
                System.out.println("Inserire un altro POI all'itinerario? y/n");
                input = in.nextLine();
            }
        }while (!input.equals("n"));
    }

    private void insertItineraryValidity(){
        System.out.println("Inserisci la data di inizio dell'itinerario nel formato dd-mm-yyyy  hh:mm");
        LocalDateTime open = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        System.out.println("Inserisci la data di fine dell'itinerario nel formato dd-mm-yyyy  hh:mm");
        LocalDateTime close = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        controller.insertItineraryValidity(open, close);
    }
    public abstract void confirmCreationItinerary();

    @Override
    public void addContent() {
        this.controller.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
        this.addContentToPOI();
        this.confirmAddContent();
    }

    private void addContentToPOI(){
        System.out.println("Seleziona l'id del POI al quale vuoi aggiungere un contenuto");
        int poiId = in.nextInt();
        in.nextLine();
        System.out.println("Inserisci il nome del contenuto");
        String name = in.nextLine();
        System.out.println("Inserisci la descrizione del contenuto");
        String desc = in.nextLine();
        System.out.println("Inserisci il file del contenuto");
        File f = fChooser.showFileChooser();
        controller.addContentToPOI(poiId, name, desc, f);
    }

    public abstract void confirmAddContent();

    public void partecipateToContest(){
        this.controller.getAllContest(contributorId).stream().forEach(contest -> System.out.println(contest.toString()));
        this.partecipateContest();
        this.insertContestContentInfo();
        System.out.println("Confermi l'inserimento del contenuto e la partecipazione al contest? y/n");
        if (in.nextLine().equals("y")){
            this.confirmPartecipation();
        }else{
            System.out.println("Inserimento del contenuto e partecipazione al contest annullati");
        }
    }

    private void confirmPartecipation() {
        this.controller.confirmPartecipation();
    }

    private void insertContestContentInfo() {
        System.out.println("Inserisci il nome del contenuto");
        String name = in.nextLine();
        System.out.println("Inserisci la descrizione del contenuto");
        String desc = in.nextLine();
        System.out.println("Inserisci il file del contenuto");
        File f = fChooser.showFileChooser();
        controller.insertContestContentInfo(name, desc, f);
        System.out.println("Contenuto inserito correttamente");
    }

    private void partecipateContest() {
        System.out.println("Inserisci l'id del contest a cui vuoi partecipare");
        int id = in.nextInt();
        in.nextLine();
        this.controller.partecipateContest(id, contributorId);
    }
}
