package it.unicam.model;

import it.unicam.model.util.ItineraryGI;
import it.unicam.model.util.POIGI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritesManager {
    private Map<Integer, List<POI>> favouritesPOI;
    private Map<Integer, List<Itinerary>> favouritesItinerary;

    public FavouritesManager() {
        this.favouritesPOI = new HashMap<>();
        this.favouritesItinerary = new HashMap<>();
    }

    public boolean addPOIToFavorites(int id, int POIid, Comune comune) {
        if (!this.favouritesPOI.containsKey(id)) {
            this.favouritesPOI.put(id, new ArrayList<>());
        }
        if (this.favouritesPOI.get(id).contains(comune.getPOI((long) POIid))) {
            return false;
        }
        this.favouritesPOI.get(id).add(comune.getPOI((long) POIid));
        return true;
    }

    public boolean addItineraryToFavorites(int id, int itineraryId, Comune comune) {
        if (!this.favouritesItinerary.containsKey(id)) {
            this.favouritesItinerary.put(id, new ArrayList<>());
        }
        if (this.favouritesItinerary.get(id).contains(comune.getItinerary(itineraryId))) {
            return false;
        }
        this.favouritesItinerary.get(id).add(comune.getItinerary(itineraryId));
          return true;
    }

    public List<POIGI> getAllFavouritesPOI(int id) {
        return this.favouritesPOI.get(id).stream().map(p -> p.getPOIGeneralInfo()).toList();
    }

    public List<ItineraryGI> getAllFavouritesItinerary(int id) {
        return this.favouritesItinerary.get(id).stream().map(i -> i.getGeneralInfoItinerary()).toList();
    }

    public void deletePOI(int id) {
        this.favouritesPOI.values().stream().forEach(p -> p.removeIf(poi -> poi.getPOIId() == id));
        //this.favouritesPOI.values().stream().forEach(p -> p.stream().filter(poi -> poi.getPOIId() > id).forEach(poi -> poi.setPOIId(poi.getPOIId() - 1)));
    }

    public void deleteItinerary(int id) {
        this.favouritesItinerary.values().stream().forEach(i -> i.removeIf(itinerary -> itinerary.getId() == id));
//        this.favouritesItinerary.values().stream().forEach(i -> i.stream().filter(it -> it.getId() > id).forEach(it -> it.setId(it.getId() - 1)));
    }
}
