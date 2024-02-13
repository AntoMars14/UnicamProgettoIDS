package it.unicam.model;

import java.util.HashMap;
import java.util.Map;

public class FavouritesManager {
    private Map<Integer, POI> favouritesPOI;
    private Map<Integer, Itinerary> favouritesItinerary;

    public FavouritesManager() {
        this.favouritesPOI = new HashMap<>();
        this.favouritesItinerary = new HashMap<>();
    }

    public boolean addPOIToFavorites(int id, int POIid, Comune comune) {
        if (this.favouritesPOI.containsValue(comune.getPOI(POIid))) {
            return false;
        }
        this.favouritesPOI.put(id, comune.getPOI(POIid));
        return true;
    }

    public boolean addItineraryToFavorites(int id, int itineraryId, Comune comune) {
        if (this.favouritesItinerary.containsValue(comune.getItinerary(itineraryId))) {
            return false;
        }
        this.favouritesItinerary.put(id, comune.getItinerary(itineraryId));
          return true;
    }
}
