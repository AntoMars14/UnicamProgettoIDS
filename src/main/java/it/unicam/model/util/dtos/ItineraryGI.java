package it.unicam.model.util.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ItineraryGI {
    private final Long id;
    private final String nome;
    private final String descrizione;
    private final LocalDateTime startDate;
    private final LocalDateTime closetDate;

    public ItineraryGI(Long id, String nome, String descrizione, LocalDateTime startDate, LocalDateTime closetDate) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.startDate = startDate;
        this.closetDate = closetDate;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Long getId() {
        return id;
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
                "\nDescrizione= " + descrizione+
                "\n"+s;
    }
}
