package it.unicam.model;
import it.unicam.model.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class Comune {

    private Coordinates coordinates;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<POI> POIValidate = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<POI> POIPending = new ArrayList<>();
    private List<Itinerary> itineraries = new ArrayList<>();
    private List<Itinerary> itinerariesPending = new ArrayList<>();

    public Comune() {
    }
    public Comune(String name, Coordinates coord) {
        this.name = name;
        this.coordinates = coord;
        POILuogo comune = new POILuogo(coord);
        comune.insertPOIInfo(name, "Questo è il comune di Camerino");
        this.insertPOI(comune);
    }

    public void insertPOI(POI p) {
        this.POIValidate.add(p);
        //p.setPOIId(this.POIValidate.indexOf(p) + 1);
    }

    public void insertPOIPending(POI p) {
        this.POIPending.add(p);
        //p.setPOIId(this.POIPending.indexOf(p) + 1);
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

    public POIFD viewSelectedPOI(int POIId) {
        return this.POIValidate.get(POIId - 1).getFullDetailedPOI();
    }

    public ContentFD viewContent(int POIId, int contentID) {
        if (POIId > 0) {
            return this.POIValidate.get(POIId - 1).getContents().get(contentID - 1).getFullDetailedContent();
        }
        return null;
    }
    public POI getPOI(int i) {
        return this.POIValidate.get(i-1);
    }

    public void insertPendingItinerary(Itinerary itinerary) {
        this.itinerariesPending.add(itinerary);
        itinerary.setId(this.itinerariesPending.indexOf(itinerary)+1);
    }

    public void insertItinerary(Itinerary itinerary) {
        this.itineraries.add(itinerary);
        itinerary.setId(this.itineraries.indexOf(itinerary)+1);
    }

    public ContentFD viewContentPOIPending(int POIId, int contentID){
        if (POIId > 0) {
            return this.POIPending.get(POIId - 1).getContents().get(contentID - 1).getFullDetailedContent();
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
        return this.POIPending.get(i-1).getFullDetailedPOI();
    }

    public void validateSelectedPOI(int POIId) {
        this.insertPOI(this.POIPending.get(POIId - 1));
        this.deletePendingPOI(POIId);
    }

    public void deletePendingPOI(int POIId) {
        this.POIPending.remove(POIId - 1);
        //this.POIPending.stream().forEach(poi -> poi.setPOIId(this.POIPending.indexOf(poi)+1));
    }

    public void deletePOI(int id) {
        this.itineraries.stream().filter(itinerary -> itinerary.getPOIs().contains(this.POIValidate.get(id - 1)))
                .forEach(itinerary -> itinerary.getPOIs().remove(this.POIValidate.get(id - 1)));
      this.itineraries.removeIf(i -> i.getPOIs().size() < 2);
        this.itinerariesPending.stream().filter(itinerary -> itinerary.getPOIs().contains(this.POIValidate.get(id - 1)))
                .forEach(itinerary -> itinerary.getPOIs().remove(this.POIValidate.get(id - 1)));
        this.itinerariesPending.removeIf(i -> i.getPOIs().size() < 2);
        this.POIValidate.remove(id - 1);
        //this.POIValidate.stream().forEach(poi -> poi.setPOIId(this.POIValidate.indexOf(poi)+1));
    }

    public void deleteItinerary(int id) {
        this.itineraries.remove(id - 1);
        this.itineraries.stream().forEach(itinerary -> itinerary.setId(this.itineraries.indexOf(itinerary)+1));
    }

    public void deleteContent(int POIId, int ContentId) {
        this.POIValidate.get(POIId -1).deleteContent(ContentId);
    }

    public List<POIGI> getAllPendingContentPOI() {
        return this.POIValidate.stream().filter(p -> p.getContentsPending().size() != 0).map(p->p.getPOIGeneralInfo()).toList();
    }

    public ContentFD selectedPendingContent(int POIId, int contentId) {
        if (POIId > 0) {
            return this.POIValidate.get(POIId-1).getContentsPending().get(contentId-1).getFullDetailedContent();
        }
        return null;
    }

    public void deletePendingContent(int POIId, int contentId) {
        this.POIValidate.get(POIId-1).deletePendingContent(contentId);
    }

    public void validateSelectedContent(int POIId, int contentId) {
        this.POIValidate.get(POIId-1).validateContent(contentId);
    }

    public List<ItineraryGI> getAllPendingItinerary() {
        List<ItineraryGI> is = new ArrayList<>();
       for(Itinerary i:this.itinerariesPending){
           is.add(i.getGeneralInfoItinerary());
       }
       return is;
    }

    public ItineraryFD selectedPendingItinerary(int i) {
        return this.itinerariesPending.get(i-1).getFullDetailedItinerary();
    }

    public void validateSelectedItinerary(int itineraryId) {
        this.itineraries.add(this.itinerariesPending.get(itineraryId-1));
        this.itinerariesPending.get(itineraryId-1).setId(this.itineraries.size());
        this.deletePendingItinerary(itineraryId);
    }

    public void deletePendingItinerary(int id) {
        this.itinerariesPending.remove(id-1);
        this.itinerariesPending.stream().forEach(itinerary -> itinerary.setId(this.itinerariesPending.indexOf(itinerary)+1));
    }

    public List<POIEvento> getAllPOIEvento() {
        return this.POIValidate.stream().filter(p -> p instanceof POIEvento).map(p -> (POIEvento) p).toList();
    }

    public List<Itinerary> getAllItinerariesWithValidity() {
        return this.itineraries.stream().filter(i -> i.getClosetDate()!=null).toList();
    }

    public Itinerary getItinerary(int itineraryId) {
        return this.itineraries.get(itineraryId-1);
    }
}