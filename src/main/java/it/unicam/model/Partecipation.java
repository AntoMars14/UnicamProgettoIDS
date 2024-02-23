package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Partecipation {
    @EmbeddedId
    private ParticipationId id;

    @ManyToOne
    @MapsId("contentId")
    private Content content;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;
}
