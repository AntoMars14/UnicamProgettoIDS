package it.unicam.model.favourites;

import it.unicam.model.POI;
import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.*;


@Entity
public class FavouritesPOI {
    @EmbeddedId
    private FavouritesPOIId id;

    @OneToOne
    @MapsId("POIId")
    private POI poi;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;


    public FavouritesPOI(POI poi, UtenteAutenticato contributor) {
        this.poi = poi;
        this.user = contributor;
        this.id = new FavouritesPOIId(poi.getPOIId(), contributor.getId());
    }

    public FavouritesPOI() {

    }

    public UtenteAutenticato getUser() {
        return user;
    }

    public POI getPoi() {
        return poi;
    }

    public FavouritesPOIId getId() {
        return id;
    }
}
