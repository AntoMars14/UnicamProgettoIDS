package it.unicam.model.favourites;

import it.unicam.model.utenti.UtentiAutenticatiManager;
import it.unicam.model.util.ItineraryGI;
import it.unicam.model.util.POIGI;
import it.unicam.repositories.FavouritesItineraryRepository;
import it.unicam.repositories.FavouritesPOIRepository;
import it.unicam.repositories.ItineraryRepository;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FavouritesManager {

    @Autowired
    private FavouritesPOIRepository favouritesPOIRepository;
    @Autowired
    private FavouritesItineraryRepository favouritesItineraryRepository;
    @Autowired
    private POIRepository poiRepository;
    @Autowired
    private ItineraryRepository itineraryRepository;
    @Autowired
    private UtentiAutenticatiManager utentiAutenticatiManager;

    public boolean addPOIToFavorites(Long id, Long POIid, Long idComune) {
        if(!this.poiRepository.existsById(POIid)){
            return false;
        }
        if (!this.favouritesPOIRepository.existsById(new FavouritesPOIId(POIid, id))) {
            this.favouritesPOIRepository.save(new FavouritesPOI(this.poiRepository.findById(POIid).get(), this.utentiAutenticatiManager.getUser(id)));
            return true;
        }
        return false;
    }


    public boolean addItineraryToFavorites(Long id, Long itineraryId, Long idComune) {
        if (!this.itineraryRepository.existsById(itineraryId)) {
            return false;
        }
        if (!this.favouritesItineraryRepository.existsById(new FavouritesItineraryId(itineraryId, id))) {
            this.favouritesItineraryRepository.save(new FavouritesItinerary(this.itineraryRepository.findById(itineraryId).get(), this.utentiAutenticatiManager.getUser(id)));
            return true;
        }
        return false;
    }


    public List<POIGI> getAllFavouritesPOI(Long id) {
        List<POIGI> list = new ArrayList<>();
        this.favouritesPOIRepository.findAll().forEach(f -> {
            if (f.getId().getUserId().equals(id)) {
                list.add(f.getPoi().getPOIGeneralInfo());
            }
        });
        return list;
    }


    public List<ItineraryGI> getAllFavouritesItinerary(Long id) {
        List<ItineraryGI> list = new ArrayList<>();
        this.favouritesItineraryRepository.findAll().forEach(f -> {
            if (f.getId().getUserId().equals(id)) {
                list.add(f.getItinerary().getGeneralInfoItinerary());
            }
        });
        return list;
    }

   public void deletePOI(Long id) {
        this.favouritesPOIRepository.findAll().forEach(f -> {
            if (f.getPoi().getPOIId().equals(id)) {
                this.favouritesPOIRepository.delete(f);
            }
        });
   }

    public void deleteItinerary(Long id) {
        this.favouritesItineraryRepository.findAll().forEach(f -> {
            if (f.getItinerary().getId().equals(id)) {
                this.favouritesItineraryRepository.delete(f);
            }
        });
    }


}
