package it.unicam.repositories;

import it.unicam.model.Itinerary;
import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long>{
}
