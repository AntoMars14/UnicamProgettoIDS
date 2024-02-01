package it.unicam.model;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;

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
        p.setPOIId(this.POIValidate.indexOf(p) + 1);
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

    public POI getPOI(int i) {
        return this.POIValidate.get(i-1);
    }

    public void insertPendingItinerary(Itinerary itinerary) {
        this.itinerariesPending.add(itinerary);
    }

    public void insertItinerary(Itinerary itinerary) {
        this.itineraries.add(itinerary);
    }
}