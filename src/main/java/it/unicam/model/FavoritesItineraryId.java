package it.unicam.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FavoritesItineraryId implements Serializable {

    private Long itineraryId;
    private Long userId;

    public FavoritesItineraryId(Long itineraryId, Long userId) {
        this.itineraryId = itineraryId;
        this.userId = userId;
    }

    public FavoritesItineraryId() {

    }

    public Long getItineraryId() {
        return itineraryId;
    }

    public Long getUserId() {
        return userId;
    }
}
