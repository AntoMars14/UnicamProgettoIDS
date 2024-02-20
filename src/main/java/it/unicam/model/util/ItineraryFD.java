package it.unicam.model.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItineraryFD {
    private final Long id;
    private final String nome;
    private final String descrizione;
    private final List<POIGI> POIGIs;
    private final LocalDateTime startDate;
    private final LocalDateTime closetDate;


    public ItineraryFD(Long id, String nome, String descrizione, LocalDateTime startDate, LocalDateTime closetDate, List<POIGI> POIGIs) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.POIGIs = POIGIs;
        this.startDate = startDate;
        this.closetDate = closetDate;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<POIGI> getPOIGIs() {
        return POIGIs;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getClosetDate() {
        return closetDate;
    }

    @Override
    public String toString() {
        String s;
        if (startDate == null || closetDate == null){
            s = "Validità: Sempre attivo";
        }else{
            s = "Data di inizio: "+this.startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))+" - "+
                    "Data di chiusura: "+this.closetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        }
        return "Id= " + id + " Nome= " + nome +
                "\nDescrizione= " + descrizione +
                "\n"+s+
                "\n"+"POI:\n"+POIGIs.stream().map(poigi -> poigi.toString()).collect(Collectors.joining("\n"));
    }
}
