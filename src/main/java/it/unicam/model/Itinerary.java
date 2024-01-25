package it.unicam.model;

import it.unicam.model.util.ItineraryFD;
import it.unicam.model.util.ItineraryGI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Itinerary {
    private int id;
    private final String nome;
    private final String descrizione;
    private LocalDateTime startDate = null;
    private LocalDateTime closetDate = null;
    private List<POI> POIs = new ArrayList();

    public Itinerary(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
