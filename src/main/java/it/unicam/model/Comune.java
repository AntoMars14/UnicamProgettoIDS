package it.unicam.model;
import it.unicam.model.util.dtos.*;
import jakarta.persistence.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class Comune {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comune_generator")
    private Long comuneId;
    private Coordinates coordinates;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<POI> POIValidate = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<POI> POIPending = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Itinerary> itineraries = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Itinerary> itinerariesPending = new ArrayList<>();



    public Comune() {
    }

    public Comune(String name, Coordinates coord) {
        this.name = name;
        this.coordinates = coord;
        POILuogo comune = new POILuogo(coord);
        comune.insertPOIInfo(name, "Questo è il punto di interesse logico del Comune di "+name);
        this.insertPOI(comune);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void insertPOI(POI p) {
        this.POIValidate.add(p);
    }

    public void insertPOIPending(POI p) {
        this.POIPending.add(p);
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
                return jsonResponse.contains("\""+this.name+"\"");
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

    public POIFD viewSelectedPOI(Long POIId) {
        if(this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().isEmpty())
            return null;
        else
            return this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get().getFullDetailedPOI();
    }

    public ContentFD viewContent(Long POIId, Long contentID) {
        if(this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().isEmpty())
            return null;
        else if(this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get().getContents().stream().filter(c -> c.getContentId().equals(contentID)).findFirst().isEmpty())
            return null;
        else
            return this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get().getContents().stream().filter(c -> c.getContentId().equals(contentID)).findFirst().get().getFullDetailedContent();
    }

    public POI getPOI(Long i) {
        return this.POIValidate.stream().filter(p -> p.getPOIId().equals(i)).findFirst().orElse(null);
    }

    public void insertPendingItinerary(Itinerary itinerary) {
        this.itinerariesPending.add(itinerary);
    }

    public void insertItinerary(Itinerary itinerary) {
        this.itineraries.add(itinerary);
    }

    public ContentFD viewContentPOIPending(Long POIId, Long contentID){
        if(this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().isEmpty())
            return null;
        else if(this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get().getContentsPending().stream().filter(c -> c.getContentId().equals(contentID)).findFirst().isEmpty())
            return null;
        else
            return this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get().getContentsPending().stream().filter(c -> c.getContentId().equals(contentID)).findFirst().get().getFullDetailedContent();
    }

    public List<ItineraryGI> getAllItinerary() {
       return this.itineraries.stream().map(itinerary -> itinerary.getGeneralInfoItinerary()).toList();
    }

    public ItineraryFD selectedItinerary(Long id) {
        if(this.itineraries.stream().filter(itinerary -> itinerary.getId().equals(id)).findFirst().isEmpty())
            return null;
        else
            return this.itineraries.stream().filter(itinerary -> itinerary.getId().equals(id)).findFirst().get().getFullDetailedItinerary();
    }

    public List<POIGI> getAllPendingPOI() {
        return this.POIPending.stream().map(poi -> poi.getPOIGeneralInfo()).toList();
    }

    public POIFD selectedPendingPOI(Long i) {
        if(this.POIPending.stream().filter(poi -> poi.getPOIId().equals(i)).findFirst().isEmpty())
            return null;
        else
            return this.POIPending.stream().filter(poi -> poi.getPOIId().equals(i)).findFirst().get().getFullDetailedPOI();
    }

    public void validateSelectedPOI(Long POIId) {
       this.insertPOI(this.POIPending.stream().filter(poi -> poi.getPOIId().equals(POIId)).findFirst().get());
       this.POIPending.removeIf(poi -> poi.getPOIId().equals(POIId));
    }

    public void deletePendingPOI(Long POIId) {
        this.POIPending.removeIf(poi -> poi.getPOIId().equals(POIId));
    }

    public void deletePOI(Long id) {
        this.itineraries.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.itinerariesPending.stream().forEach(itinerary -> itinerary.getPOIs().removeIf(p -> p.getPOIId().equals(id)));
        this.POIValidate.removeIf(poi -> poi.getPOIId().equals(id));
    }

    public List<Long> notItinerary(){
        return this.itineraries.stream().filter(i -> i.getPOIs().size() < 2).map(i -> i.getId()).toList();
    }
    public void deleteNotItinerary(){
        this.itineraries.removeIf(i -> i.getPOIs().size() < 2);
        this.itinerariesPending.removeIf(i -> i.getPOIs().size() < 2);
    }

    public void deleteItinerary(Long id) {
        this.itineraries.removeIf(itinerary -> itinerary.getId().equals(id));
    }

    public void deleteContent(Long POIId, Long ContentId) {
        this.POIValidate.stream().filter(poi -> poi.getPOIId().equals(POIId)).findFirst().get().deleteContent(ContentId);
    }

    public List<POIGI> getAllPendingContentPOI() {
        return this.POIValidate.stream().filter(p -> !p.getContentsPending().isEmpty()).map(p->p.getPOIGeneralInfo()).toList();
    }

    public ContentFD selectedPendingContent(Long POIId, Long contentId) {
        return this.POIValidate.stream().filter(p -> p.getPOIId().equals(POIId)).findFirst().get()
                .getContentsPending().stream().filter(c -> c.getContentId().equals(contentId)).findFirst().get().getFullDetailedContent();
    }

    public void deletePendingContent(Long POIId, Long contentId) {
        POI p = this.POIValidate.stream().filter(poi -> poi.getPOIId().equals(POIId)).findFirst().get();
        p.deletePendingContent(contentId);
    }


    public void validateSelectedContent(Long POIId, Long contentId) {
        this.POIValidate.stream().filter(poi -> poi.getPOIId().equals(POIId)).findFirst().get().validateContent(contentId);
    }

    public List<ItineraryGI> getAllPendingItinerary() {
        List<ItineraryGI> is = new ArrayList<>();
       for(Itinerary i:this.itinerariesPending){
           is.add(i.getGeneralInfoItinerary());
       }
       return is;
    }

    public ItineraryFD selectedPendingItinerary(Long id) {
        if(!this.itinerariesPending.stream().filter(itinerary -> itinerary.getId().equals(id)).findFirst().isEmpty())
           return this.itinerariesPending.stream().filter(itinerary -> itinerary.getId().equals(id)).findFirst().get().getFullDetailedItinerary();
        else
            return null;
    }

    public void validateSelectedItinerary(Long itineraryId) {
        this.itineraries.add(this.itinerariesPending.stream().filter(itinerary -> itinerary.getId().equals(itineraryId)).findFirst().get());
        this.itinerariesPending.removeIf(itinerary -> itinerary.getId().equals(itineraryId));
    }

    public void deletePendingItinerary(Long id) {
        this.itinerariesPending.removeIf(itinerary -> itinerary.getId().equals(id));
    }

    public Itinerary getItinerary(Long itineraryId) {
        return this.itineraries.stream().filter(i -> i.getId().equals(itineraryId)).findFirst().orElse(null);
    }
}