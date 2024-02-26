package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.*;

@Entity
public class FavoritesItinerary {
    @EmbeddedId
    private FavoritesItineraryId id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("itineraryId")
    private Itinerary itinerary;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;


    public FavoritesItinerary(Itinerary itinerary, UtenteAutenticato contributor) {
        this.itinerary = itinerary;
        this.user = contributor;
        this.id = new FavoritesItineraryId(itinerary.getId(), contributor.getId());
    }

    public FavoritesItinerary() {

    }

    public UtenteAutenticato getUser() {
        return user;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public FavoritesItineraryId getId() {
        return id;
    }
}



