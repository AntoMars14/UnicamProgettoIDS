package it.unicam.model.controllersGRASP;

import it.unicam.model.*;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;
import it.unicam.repositories.POIRepository;
import it.unicam.view.io.MapHandler;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class POIController {
    private Comune c;

    private Coordinates lastCoords;

    private POI lastPOI;

    @Autowired
    private POIRepository poiRepository;

    public POIController(Comune c) {
        this.c = c;
        this.lastCoords = null;
        this.lastPOI = null;
    }

    public List<POIGI> getAllPoi(){
        return c.getAllPOI();
    }

    public MapHandler Map(){
        List<POIGI> pois = this.getAllPoi();
        System.out.println(pois.size());
        List<Coordinates> coords = this.getAllPoi().stream().map(p -> p.getCoordinates()).toList();
        return new MapHandler(coords);
    }

    public boolean selectPoint(ICoordinate i){
        this.lastCoords = new Coordinates(i.getLat(), i.getLon());
        return (c.isInComune(this.lastCoords)&&(!c.thereIsPOI(this.lastCoords)));
    }

    public void InsertPoiInfo(String name, String description){
        this.lastPOI.insertPOIInfo(name, description);
    }

    public void selectType(POIFactory f){
        this.lastPOI = f.createPOI(this.lastCoords);
    }

    public void insertTime(LocalTime[] openingTime, LocalTime[] closingTime){

        if (this.lastPOI instanceof POILuogoConOra p) {
            p.insertTime(openingTime, closingTime);
        }
    }

    public void insertDate(LocalDateTime openingDate, LocalDateTime closingDate){
        if(this.lastPOI instanceof POIEvento p){
            p.insertDate(openingDate, closingDate);
        }
    }

    public void insertContent(String name, String description, File file){
        this.lastPOI.addContent(new Content(name, description, file,this.lastPOI.getContents().size()+1));
    }

    public void confirmPoi(){
        c.insertPOI(this.lastPOI);
    }

    public void confirmPoiPending(){
        c.insertPOIPending(this.lastPOI);
    }

    public void insertPOI(POIFactory pf, POIFD p) {
        POI poi = pf.createPOI(p.getCoordinates());
        poi.insertPOIInfo(p.getName(), p.getDescription());
        if (poi instanceof POILuogoConOra plo) {
            plo.insertTime(p.getOpeningTime(), p.getClosingTime());
        }
        if (poi instanceof POIEvento pe) {
            pe.insertDate(p.getOpeningDate(), p.getClosingDate());
        }
        this.poiRepository.save(poi);
        this.c.insertPOI(poi);
    }
}
