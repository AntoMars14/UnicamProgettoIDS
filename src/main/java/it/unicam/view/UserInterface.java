package it.unicam.view;

import it.unicam.model.*;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.POIFD;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {
    private Comune c;
    private POIController p;
    private Scanner in = new Scanner(System.in);

    private Desktop desktop = Desktop.getDesktop();

    private FileChooser fChooser = new FileChooser();

    public UserInterface(Comune c) {
        this.c = c;
        this.p = new POIController(c);
    }

    public void viewPOI() throws IOException {
        c.getAllPOI().stream().forEach(p -> System.out.println(p.toString()));
        int poiId = in.nextInt();
        POIFD fd = this.selectedPOI(poiId);
        System.out.println(fd.toString());
        int input = -1;
        while (input != 0){
            System.out.println("Per visualizzare un contenuto inserire il suo id, per terminare di visualizzare contenuti inserire 0");
            input = in.nextInt();
            if(input>0) {
                ContentFD x = this.selectedContent(input);
                desktop.open(x.getFile());
            }
        }
    }



    public POIFD selectedPOI(int poiID){
        return c.viewSelectedPOI(poiID);
    }

    public ContentFD selectedContent(int idContent){
        return c.viewContent(idContent);
    }

    public void insertPOI(){
        System.out.println("Seleziona le coordinate sulla mappa del punto di interesse da inserire\n");
        MapHandler map = p.Map();
        if(!this.selectPoint(map.showMap())){
            System.out.println("Coordinate selezionate non valide, seleziona coordinate nel comune e a cui non è già associato un POI");
            this.insertPOI();
        }
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
        System.out.println("Inserisci il nome del POI");
        String name = in.nextLine();
        System.out.println("Inserisci la descrizione del POI");
        String desc = in.nextLine();
        this.insertPOIInfo(name, desc);
        if(t.equals(Type.LUOGOCONORA)){
            LocalTime[] opent = new LocalTime[7];
            LocalTime[] closet = new LocalTime[7];
            System.out.println("Inserisci orario di apertura e chiusura(a partire da Lunedì)");
            for (int i = 0; i < 6; i++){
                System.out.println("Inserisci ora di apertura nel formato HH:mm");
                opent[i] = LocalTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println("Inserisci ora di chiusura nel formato HH:mm");
                closet[i] = LocalTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            this.insertTime(opent, closet);
        }
        if(t.equals(Type.EVENTO)){
            System.out.println("Inserire la data di apertura nel formato gg-mm-yyyy hh:mm");
            LocalDateTime opend = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            System.out.println("Inserire la data di chiusura nel formato gg-mm-yyyy hh:mm");
            LocalDateTime closed = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            this.insertDate(opend, closed);
        }
        String exit = "";
        while(!exit.equals("n")){
            System.out.println("Vuoi inserire un contenuto? y/n");
            exit = in.nextLine();
            if(!exit.equals("n")){
                System.out.println("Inserisci il nome del contenuto");
                String n = in.nextLine();
                System.out.println("Inserisci la descrizione del contenuto");
                String d = in.nextLine();
                System.out.println("Inserisci il file del contenuto");
                File f = fChooser.showFileChooser();
                this.insertContent(n, d, f);
            }
        }
    }

    public boolean selectPoint(ICoordinate c){
        return p.selectPoint(c);
    }
    public void insertPOIInfo(String name, String description){
        p.InsertPoiInfo(name, description);
    }

    public void selectType(Type t){
        switch (t){
            case Type.LUOGO -> p.selectType(new POILuogoFactory());
            case Type.LUOGOCONORA -> p.selectType(new POILuogoConOraFactory());
            case Type.EVENTO -> p.selectType(new POIEventoFactory());
        }
    }

    public void insertTime(LocalTime[] openingTime, LocalTime[] closingTime){
        p.insertTime(openingTime, closingTime);
    }

    public void insertDate(LocalDateTime openingDate, LocalDateTime closingDate){
        p.insertDate(openingDate, closingDate);
    }
    public void insertContent(String name, String description, File file){
        p.insertContent(name, description, file);
    }

    public void confirmPOI(){
        p.confirmPoi();
    }

    public void confirmPOIPending(){
        p.confirmPoiPending();
    }
}
