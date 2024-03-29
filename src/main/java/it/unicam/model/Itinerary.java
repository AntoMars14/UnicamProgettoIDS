package it.unicam.model;

import it.unicam.model.util.dtos.ItineraryFD;
import it.unicam.model.util.dtos.ItineraryGI;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itinerary_id_seq")
    private Long id;
    private String nome;
    private String descrizione;
    private LocalDateTime startDate = null;
    private LocalDateTime closetDate = null;
    @ManyToMany
    private List<POI> POIs;

    public Itinerary(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.POIs = new ArrayList<>();
    }

    public Itinerary() {
        this.POIs = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getClosetDate() {
        return closetDate;
    }

    public void setClosetDate(LocalDateTime closetDate) {
        this.closetDate = closetDate;
    }

    public List<POI> getPOIs() {
        return POIs;
    }

    public void setPOIs(List<POI> POIs) {
        this.POIs = POIs;
    }

    public Long getId() {
        return id;
    }


    public ItineraryGI getGeneralInfoItinerary(){
        return new ItineraryGI(this.id, this.nome, this.descrizione, this.startDate, this.closetDate);
    }

    public ItineraryFD getFullDetailedItinerary(){
        return new ItineraryFD(this.id, this.nome, this.descrizione, this.startDate, this.closetDate, this.POIs.stream().map(poi -> poi.getPOIGeneralInfo()).toList());
    }

    public void addPOI(POI p){
        this.POIs.add(p);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Itinerary)) return false;
        Itinerary itinerary = (Itinerary) obj;
        return getId().equals(itinerary.getId());
    }
}
