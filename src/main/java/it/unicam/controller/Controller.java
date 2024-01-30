package it.unicam.controller;

import it.unicam.model.Comune;
import it.unicam.model.POIController;
import it.unicam.model.POIFactory;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;
import it.unicam.view.io.MapHandler;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Controller {

    private Comune comune;
    private POIController poiController;

    public Controller(Comune comune) {
        this.comune = comune;
        this.poiController = new POIController(comune);
    }

    public List<POIGI> getAllPOI(){
        return comune.getAllPOI();
    }

    public POIFD viewSelectedPOI(int poiID){
        return comune.viewSelectedPOI(poiID);
    }

    public ContentFD viewContent(int idContent){
       return comune.viewContent(idContent);
    }

    public MapHandler map(){
        return poiController.Map();
    }

    public boolean selectPoint(ICoordinate c) {
        return poiController.selectPoint(c);
    }

    public void insertPoiInfo(String name, String desc){
        poiController.InsertPoiInfo(name, desc);
    }

    public void selectType(POIFactory p){
        poiController.selectType(p);
    }

    public void insertTime(LocalTime[] opent, LocalTime[] closet){
        poiController.insertTime(opent, closet);
    }

    public void insertDate(LocalDateTime opend, LocalDateTime closed){
        poiController.insertDate(opend, closed);
    }

    public void insertContent(String n, String d, File f){
        poiController.insertContent(n, d, f);
    }

    public void confirmPoi(){
        poiController.confirmPoi();
    }

    public void confirmPoiPending(){
        poiController.confirmPoiPending();
    }

}
