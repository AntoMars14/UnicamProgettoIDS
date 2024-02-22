package it.unicam.model.controllersGRASP;

import it.unicam.model.*;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;
import it.unicam.repositories.ComuneRepository;
import it.unicam.repositories.POIRepository;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class POIController {

    @Autowired
    private ComuneRepository comuneRepository;


    public boolean selectPoint(Long idComune, Coordinates c){
        //return (this.comuneRepository.findById(idComune).get().isInComune(c)&&(!this.comuneRepository.findById(idComune).get().thereIsPOI(c)));
        return true;
    }


    public void insertPOI(Long idComune, POIFactory pf, POIFD p) {
        POI poi = pf.createPOI(p.getCoordinates());
        poi.insertPOIInfo(p.getName(), p.getDescription());
        if (poi instanceof POILuogoConOra plo) {
            plo.insertTime(p.getOpeningTime(), p.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.insertDate(p.getOpeningDate(), p.getClosingDate());
        }
        Comune c = this.comuneRepository.findById(idComune).get();
        c.insertPOI(poi);
        this.comuneRepository.save(c);
    }

    public void insertPOIPending(Long idComune, POIFactory pf, POIFD p) {
        POI poi = pf.createPOI(p.getCoordinates());
        poi.insertPOIInfo(p.getName(), p.getDescription());
        if (poi instanceof POILuogoConOra plo) {
            plo.insertTime(p.getOpeningTime(), p.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.insertDate(p.getOpeningDate(), p.getClosingDate());
        }
        Comune c = this.comuneRepository.findById(idComune).get();
        c.insertPOIPending(poi);
        this.comuneRepository.save(c);
    }
}
