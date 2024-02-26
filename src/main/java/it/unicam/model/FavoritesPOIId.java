package it.unicam.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FavoritesPOIId implements Serializable {
    private Long POIId;
    private Long userId;

    public FavoritesPOIId(Long POIId, Long userId) {
        this.POIId = POIId;
        this.userId = userId;
    }

    public FavoritesPOIId() {

    }

    public Long getPOIId() {
        return POIId;
    }

    public Long getUserId() {
        return userId;
    }
}
