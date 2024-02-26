package it.unicam.model.favourites;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FavouritesPOIId implements Serializable {
    private Long POIId;
    private Long userId;

    public FavouritesPOIId(Long POIId, Long userId) {
        this.POIId = POIId;
        this.userId = userId;
    }

    public FavouritesPOIId() {

    }

    public Long getPOIId() {
        return POIId;
    }

    public Long getUserId() {
        return userId;
    }
}
