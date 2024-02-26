package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.*;


@Entity
public class FavoritesPOI {
    @EmbeddedId
    private FavoritesPOIId id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("POIId")
    private POI poi;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;


    public FavoritesPOI(POI poi, UtenteAutenticato contributor) {
        this.poi = poi;
        this.user = contributor;
        this.id = new FavoritesPOIId(poi.getPOIId(), contributor.getId());
    }

    public FavoritesPOI() {

    }

    public UtenteAutenticato getUser() {
        return user;
    }

    public POI getPoi() {
        return poi;
    }

    public FavoritesPOIId getId() {
        return id;
    }
}
