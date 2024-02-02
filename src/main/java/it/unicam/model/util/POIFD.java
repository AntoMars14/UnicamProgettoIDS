package it.unicam.model.util;

import it.unicam.model.Coordinates;
import it.unicam.model.Type;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class POIFD {
    private final int id;
    private final String name;
    private final String description;
    private final Type type;
    private final Coordinates coordinates;
    private final LocalTime[] openingTime;
    private final LocalTime[] closingTime;
    private final LocalDateTime openingDate;
    private final LocalDateTime closingDate;
    private final List<ContentGI> contentsGI;
    private final List<ContentGI> pendingContentsGI;

    public POIFD(int id, String name, String description, Coordinates coord, Type type, List<ContentGI> contentsGI, List<ContentGI> pendingContentsGI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coord;
        this.type = type;
        this.contentsGI = contentsGI;
        this.pendingContentsGI = pendingContentsGI;
        this.openingTime = null;
        this.closingTime = null;
        this.openingDate = null;
        this.closingDate = null;
    }

    public POIFD(int id, String name, String description, Coordinates coord, Type type, LocalTime[] ot, LocalTime[] ct, List<ContentGI> contentsGI, List<ContentGI> pendingContentsGI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coord;
        this.type = type;
        this.contentsGI = contentsGI;
        this.openingTime = ot;
        this.closingTime = ct;
        this.pendingContentsGI = pendingContentsGI;
        this.openingDate = null;
        this.closingDate = null;
    }

    public POIFD(int id, String name, String description, Coordinates coord, Type type, LocalDateTime od, LocalDateTime cd, List<ContentGI> contentsGI, List<ContentGI> pendingContentsGI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coord;
        this.type = type;
        this.contentsGI = contentsGI;
        this.pendingContentsGI = pendingContentsGI;
        this.openingTime = null;
        this.closingTime = null;
        this.openingDate = od;
        this.closingDate = cd;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public LocalTime[] getOpeningTime() {
        return openingTime;
    }

    public LocalTime[] getClosingTime() {
        return closingTime;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<ContentGI> getContentsGI() {
        return contentsGI;
    }

    public List<ContentGI> getPendingContentsGI() {
        return pendingContentsGI;
    }

    @Override
    public String toString() {
        List<String> cont = this.getContentsGI().stream().map(c -> c.toString()).toList();
        return switch(type){
            case LUOGO -> "Id: "+this.id+" Nome: "+this.name+"\nDescrizione: "+this.description+"\nTipologia: "+this.type+"\nCoordinates: lat "
                    +this.coordinates.getLat()+" lon "+this.coordinates.getLon()+"\n"+"Contenuti:\n"
                    + cont.stream().collect(Collectors.joining("\n"));
            case LUOGOCONORA -> "Id: "+this.id+" Nome: "+this.name+"\nDescrizione: "+this.description+"\nTipologia: "+this.type+"\nCoordinates: lat "
                    +this.coordinates.getLat()+" lon "+this.coordinates.getLon()+"\nOrari:"+
                    "\nLunedì: "+this.openingTime[0]+" - "+this.closingTime[0]+"\nMartedì: "+this.openingTime[1]+" - "+this.closingTime[1]+
                    "\nMercoledì: "+this.openingTime[2]+" - "+this.closingTime[2]+"\nGiovedì: "+this.openingTime[3]+" - "+this.closingTime[3]+
                    "\nVenerdì: "+this.openingTime[4]+" - "+this.closingTime[4]+"\nSabato: "+this.openingTime[5]+" - "+this.closingTime[5]+
                    "\nDomenica: "+this.openingTime[6]+" - "+this.closingTime[6]+
                    "\nContenuti:\n" + cont.stream().collect(Collectors.joining("\n"));
            case EVENTO -> "Id: "+this.id+" Nome: "+this.name+"\nDescrizione: "+this.description+"\nTipologia: "+this.type+"\nCoordinates: lat "
                    +this.coordinates.getLat()+" lon "+this.coordinates.getLon()+"\n"+
                    "Inizio evento: "+this.openingDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " - chiusura evento: "
                    +this.closingDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))+
                    "\nContenuti:\n" + cont.stream().collect(Collectors.joining("\n"));

        };
    }
}
