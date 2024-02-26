package it.unicam.model.controllersGRASP;

import it.unicam.model.util.dtos.ContentFD;
import it.unicam.model.util.dtos.ItineraryFD;
import it.unicam.model.util.dtos.POIFD;
import it.unicam.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewController {
    @Autowired
    private ComuneRepository comuneRepository;

    public POIFD viewSelectedPOI(Long idComune, Long POIId) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().viewSelectedPOI(POIId);
    }

    public ContentFD viewContent(Long idComune, Long idPOI, Long idContent) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().viewContent(idPOI, idContent);
    }

    public ItineraryFD selectedItinerary(Long idComune, Long idItinerary) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().selectedItinerary(idItinerary);
    }


    public ContentFD viewContentPOIPending(Long idComune, Long idPOI, Long contentID){
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().viewContentPOIPending(idPOI, contentID);
    }


    public POIFD selectedPendingPOI(Long idComune, Long id) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().selectedPendingPOI(id);
    }

    public ContentFD selectedPendingContent(Long idComune, Long idPOI, Long contentId) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().selectedPendingContent(idPOI, contentId);
    }

    public ItineraryFD selectedPendingItinerary(Long idComune, Long id) {
        if (this.comuneRepository.findById(idComune).isEmpty())
            return null;
        else
            return this.comuneRepository.findById(idComune).get().selectedPendingItinerary(id);
    }
}
