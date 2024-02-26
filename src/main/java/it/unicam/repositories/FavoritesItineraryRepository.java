package it.unicam.repositories;

import it.unicam.model.FavoritesItinerary;
import it.unicam.model.FavoritesItineraryId;
import org.springframework.data.repository.CrudRepository;

public interface FavoritesItineraryRepository extends CrudRepository<FavoritesItinerary, FavoritesItineraryId> {

}
