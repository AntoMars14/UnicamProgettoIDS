package it.unicam.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ParticipationId implements Serializable {
    private Long contentId;
    private Long userId;
}
