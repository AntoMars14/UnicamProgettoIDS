package it.unicam.model;
import it.unicam.model.util.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Comune {

    private Coordinates coordinates;
    private String name;
    private List<POI> POIValidate = new ArrayList<>();
    private List<POI> POIPending = new ArrayList<>();
    private List<Itinerary> itineraries = new ArrayList<>();
    private List<Itinerary> itinerariesPending = new ArrayList<>();
    private POIFD lastViewedPOI;

    public Comune(String name, Coordinates coord) {
        this.name = name;
        this.coordinates = coord;
        POILuogo comune = new POILuogo(coord);
        comune.insertPOIInfo(name, "Questo è il comune di Camerino");
        this.insertPOI(comune);
        this.lastViewedPOI = null;
    }

    public void insertPOI(POI p) {
        this.POIValidate.add(p);
        p.setPOIId(this.POIValidate.indexOf(p) + 1);
    }

    public void insertPOIPending(POI p) {
        this.POIPending.add(p);
        p.setPOIId(this.POIPending.indexOf(p) + 1);
    }

    public List<POI> getPOIValidate() {
        return POIValidate;
    }

    public List<POI> getPOIPending() {
        return POIPending;

    }

    public List<POIGI> getAllPOI() {
        List<POIGI> pois = new ArrayList<>();
        for (POI p :
                this.POIValidate) {
            pois.add(p.getPOIGeneralInfo());
        }
        return pois;
    }

    public boolean isInComune(Coordinates coord) {
        String apiUrl = String.format(Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%.6f&lon=%.6f&zoom=10&addressdetails=1",
                coord.getLat(), coord.getLon());

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonResponse = new String(connection.getInputStream().readAllBytes());
                return jsonResponse.contains("\"Camerino\"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean thereIsPOI(Coordinates c) {
        for (POI p : this.POIValidate) {
            if (p.getCoord().equals(c))
                return true;
        }
        for (POI p : this.POIPending) {
            if (p.getCoord().equals(c))
                return true;
        }
        return false;
    }

    public List<POIGI> viewPOI(Type type) {
        List<POIGI> pois = new ArrayList<>();
        for (POI p :
                this.POIValidate) {
            if (p.getType().equals(type)) pois.add(p.getPOIGeneralInfo());
        }
        return pois;
    }

    public POIFD viewSelectedPOI(int POIId) {
        lastViewedPOI = this.POIValidate.get(POIId - 1).getFullDetailedPOI();
        return lastViewedPOI;
    }

    public ContentFD viewContent(int contentID) {
        if (this.lastViewedPOI != null) {
            return this.POIValidate.get(this.lastViewedPOI.getId() - 1).getContents().get(contentID - 1).getFullDetailedContent();
        }
        return null;
    }

    public ContentFD viewContentPending(int contentID){
        if (this.lastViewedPOI != null) {
            return this.POIPending.get(this.lastViewedPOI.getId() - 1).getContents().get(contentID - 1).getFullDetailedContent();
        }
        return null;
    }

    public List<ItineraryGI> getAllItinerary() {
       return this.itineraries.stream().map(itinerary -> itinerary.getGeneralInfoItinerary()).toList();
    }

    public ItineraryFD selectedItinerary(int i) {
        return this.itineraries.get(i-1).getFullDetailedItinerary();
    }

    public List<POIGI> getAllPendingPOI() {
        return this.POIPending.stream().map(poi -> poi.getPOIGeneralInfo()).toList();
    }

    public POIFD selectedPendingPOI(int i) {
        this.lastViewedPOI = this.POIPending.get(i-1).getFullDetailedPOI();
        return this.lastViewedPOI;
    }

    public void validateSelectedPOI() {
        this.insertPOI(this.POIPending.get(this.lastViewedPOI.getId()-1));
        this.deletePendingPOI();
    }

    public void deletePendingPOI() {
        this.POIPending.remove(this.lastViewedPOI.getId()-1);
        this.POIPending.stream().forEach(poi -> poi.setPOIId(this.POIPending.indexOf(poi)+1));
    }

}