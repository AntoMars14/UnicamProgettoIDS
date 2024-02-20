package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Itinerary;
import it.unicam.model.util.ItineraryFD;
import it.unicam.repositories.ItineraryRepository;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItineraryController {
    private Comune comune;
    private Itinerary lastItinerary;
    @Autowired
    private ItineraryRepository itineraryRepository;
    @Autowired
    private POIRepository poiRepository;

    public ItineraryController(Comune comune) {
        this.comune = comune;
    }

    public void insertItineraryInfo(String name, String description) {
        this.lastItinerary = new Itinerary(name, description);
    }

    public void addPOI(int i) {
        this.lastItinerary.addPOI(this.comune.getPOI((long) i));
    }

    public void insertItineraryValidity(LocalDateTime open, LocalDateTime close) {
        this.lastItinerary.setStartDate(open);
        this.lastItinerary.setClosetDate(close);
    }

    public void confirmCreationPendingItinerary() {
        comune.insertPendingItinerary(this.lastItinerary);
    }

    public void confirmCreationItinerary() {
        comune.insertItinerary(this.lastItinerary);
    }


    public void createItinerary(ItineraryFD i, Long[] pois) {
        Itinerary it = new Itinerary(i.getNome(), i.getDescrizione());
        for (Long poi : pois) {
            it.addPOI(this.poiRepository.findById(poi).orElse(null));
        }
        this.itineraryRepository.save(it);
        this.comune.insertItinerary(it);
    }
}
