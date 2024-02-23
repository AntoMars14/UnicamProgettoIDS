package it.unicam.repositories;

import it.unicam.model.Partecipation;
import it.unicam.model.ParticipationId;
import org.springframework.data.repository.CrudRepository;

public interface PartecipationRepository extends CrudRepository<Partecipation, ParticipationId> {
}
