package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import jakarta.persistence.*;

@Entity
public class Partecipation {
    @EmbeddedId
    private ParticipationId id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("contentId")
    private Content content;

    @ManyToOne
    @MapsId("userId")
    private UtenteAutenticato user;

    public Partecipation(Content content, UtenteAutenticato contributor) {
        this.content = content;
        this.user = contributor;
        this.id = new ParticipationId(content.getContentId(), contributor.getId());
    }

    public Partecipation() {

    }

    public UtenteAutenticato getUser() {
        return user;
    }
}
