package it.unicam.model.favourites;

import it.unicam.model.Itinerary;
import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.*;

@Entity
public class FavouritesItinerary {
    @EmbeddedId
    private FavouritesItineraryId id;

    @OneToOne
    @MapsId("itineraryId")
    private Itinerary itinerary;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;


    public FavouritesItinerary(Itinerary itinerary, UtenteAutenticato contributor) {
        this.itinerary = itinerary;
        this.user = contributor;
        this.id = new FavouritesItineraryId(itinerary.getId(), contributor.getId());
    }

    public FavouritesItinerary() {

    }

    public UtenteAutenticato getUser() {
        return user;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public FavouritesItineraryId getId() {
        return id;
    }
}



