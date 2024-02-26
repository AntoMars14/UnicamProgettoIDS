package it.unicam.repositories;

import it.unicam.model.FavoritesPOI;
import it.unicam.model.FavoritesPOIId;
import org.springframework.data.repository.CrudRepository;

public interface FavoritesPOIRepository extends CrudRepository<FavoritesPOI, FavoritesPOIId>{
}
