package it.unicam.repositories;

import it.unicam.model.favourites.FavouritesItinerary;
import it.unicam.model.favourites.FavouritesItineraryId;
import org.springframework.data.repository.CrudRepository;

public interface FavouritesItineraryRepository extends CrudRepository<FavouritesItinerary, FavouritesItineraryId> {

}
