package it.unicam.model;

import java.time.LocalDateTime;

public class ItineraryController {
    private Comune comune;
    private Itinerary lastItinerary;

    public ItineraryController(Comune comune) {
        this.comune = comune;
    }

    public void insertItineraryInfo(String name, String description) {
        this.lastItinerary = new Itinerary(name, description);
    }

    public void addPOI(int i) {
        this.lastItinerary.addPOI(this.comune.getPOI(i));
    }

    public void insertItineraryValidity(LocalDateTime open, LocalDateTime close) {
        this.lastItinerary.setStartDate(open);
        this.lastItinerary.setClosetDate(close);
    }

    public void confirmCreationPendingItinerary() {
        comune.insertPendingItinerary(this.lastItinerary);
    }

    public void confirmCreationItinerary() {
        comune.insertItinerary(this.lastItinerary);
    }
}
