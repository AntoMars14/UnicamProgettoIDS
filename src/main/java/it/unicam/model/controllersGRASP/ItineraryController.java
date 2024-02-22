package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Itinerary;
import it.unicam.model.util.ItineraryFD;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.ItineraryRepository;
import it.unicam.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryController {
    @Autowired
    private POIRepository poiRepository;
    @Autowired
    private ComuneRepository comuneRepository;


    public void createItinerary(Long idComune, ItineraryFD i, Long[] pois) {
        Itinerary it = new Itinerary(i.getNome(), i.getDescrizione());
        if(i.getStartDate()!=null && i.getClosetDate()!=null){
            it.setStartDate(i.getStartDate());
            it.setClosetDate(i.getClosetDate());
        }
        for (Long poi : pois) {
            it.addPOI(this.poiRepository.findById(poi).orElse(null));
        }
        Comune c = this.comuneRepository.findById(idComune).get();
        c.insertItinerary(it);
        this.comuneRepository.save(c);
    }

    public void createPendingItinerary(Long idComune, ItineraryFD i, Long[] pois) {
        Itinerary it = new Itinerary(i.getNome(), i.getDescrizione());
        if(i.getStartDate()!=null && i.getClosetDate()!=null){
            it.setStartDate(i.getStartDate());
            it.setClosetDate(i.getClosetDate());
        }
        for (Long poi : pois) {
            it.addPOI(this.poiRepository.findById(poi).orElse(null));
        }
        Comune c = this.comuneRepository.findById(idComune).get();
        c.insertPendingItinerary(it);
        this.comuneRepository.save(c);
    }
}
