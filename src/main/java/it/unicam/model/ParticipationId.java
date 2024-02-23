package it.unicam.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ParticipationId implements Serializable {
    private Long contentId;
    private Long userId;

    public ParticipationId(Long contentId, Long userId) {
        this.contentId = contentId;
        this.userId = userId;
    }

    public ParticipationId() {

    }
}
