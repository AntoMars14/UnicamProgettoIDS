package it.unicam.model.favourites;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FavouritesItineraryId implements Serializable {

    private Long itineraryId;
    private Long userId;

    public FavouritesItineraryId(Long itineraryId, Long userId) {
        this.itineraryId = itineraryId;
        this.userId = userId;
    }

    public FavouritesItineraryId() {

    }

    public Long getItineraryId() {
        return itineraryId;
    }

    public Long getUserId() {
        return userId;
    }
}
