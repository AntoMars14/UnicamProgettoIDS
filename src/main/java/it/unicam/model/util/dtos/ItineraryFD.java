package it.unicam.model.util.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ItineraryFD {
    private final Long id;
    @NotNull
    @NotBlank
    private final String nome;
    @NotNull
    @NotBlank
    private final String descrizione;
    private final List<POIGI> POIGIs;
    private final LocalDateTime startDate;
    private final LocalDateTime closedDate;


    public ItineraryFD(Long id, String nome, String descrizione, LocalDateTime startDate, LocalDateTime closedDate, List<POIGI> POIGIs) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.POIGIs = POIGIs;
        this.startDate = startDate;
        this.closedDate = closedDate;
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

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    @Override
    public String toString() {
        String s;
        if (startDate == null || closedDate == null){
            s = "Validità: Sempre attivo";
        }else{
            s = "Data di inizio: "+this.startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))+" - "+
                    "Data di chiusura: "+this.closedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        }
        return "Id= " + id + " Nome= " + nome +
                "\nDescrizione= " + descrizione +
                "\n"+s+
                "\n"+"POI:\n"+POIGIs.stream().map(poigi -> poigi.toString()).collect(Collectors.joining("\n"));
    }
}
