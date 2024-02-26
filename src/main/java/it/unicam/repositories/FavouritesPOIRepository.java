package it.unicam.repositories;

import it.unicam.model.favourites.FavouritesPOI;
import it.unicam.model.favourites.FavouritesPOIId;
import org.springframework.data.repository.CrudRepository;

public interface FavouritesPOIRepository extends CrudRepository<FavouritesPOI, FavouritesPOIId>{
}
