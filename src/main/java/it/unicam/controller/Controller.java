package it.unicam.controller;

import it.unicam.model.*;
import it.unicam.model.controllersGRASP.ContentController;
import it.unicam.model.controllersGRASP.ItineraryController;
import it.unicam.model.controllersGRASP.POIController;
import it.unicam.model.controllersGRASP.ViewController;
import it.unicam.model.util.*;
import it.unicam.view.io.MapHandler;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Controller {
    private Comune comune;
    private POIController poiController;

    private ItineraryController itineraryController;

    private ContentController contentController;

    private ViewController viewController;

    public Controller(Comune comune) {
        this.comune = comune;
        this.poiController = new POIController(comune);
        this.itineraryController = new ItineraryController(comune);
        this.contentController = new ContentController(comune);
        this.viewController = new ViewController(comune);
    }

    public List<POIGI> getAllPOI(){
        return comune.getAllPOI();
    }

    public POIFD viewSelectedPOI(int poiID){
        return viewController.viewSelectedPOI(poiID);
    }

    public ContentFD viewContent(int idContent){
       return viewController.viewContent(idContent);
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

    public void insertItineraryInfo(String name, String description) {
        itineraryController.insertItineraryInfo(name, description);

    }

    public void addPOI(int i) {
        itineraryController.addPOI(i);
    }

    public void insertItineraryValidity(LocalDateTime open, LocalDateTime close) {
        itineraryController.insertItineraryValidity(open, close);
    }

    public void confirmCreationPendingItinerary() {
        itineraryController.confirmCreationPendingItinerary();
    }

    public void confirmCreationItinerary() {
        itineraryController.confirmCreationItinerary();
    }
    public List<ItineraryGI> getAllItinerary() {
       return comune.getAllItinerary();
    }

    public ItineraryFD selectedItinerary(int i) {
        return viewController.selectedItinerary(i);
    }

    public List<POIGI> getAllPendingPOI() {
        return comune.getAllPendingPOI();
    }

    public POIFD selectedPendingPOI(int i) {
        return viewController.selectedPendingPOI(i);
    }

    public void validateSelectedPOI() {
        comune.validateSelectedPOI(viewController.getLastViewedPoi().getId());
    }

    public void deletePendingPOI() {
        comune.deletePendingPOI(viewController.getLastViewedPoi().getId());
    }

    public ContentFD viewContentPending(int contentID){
        return viewController.viewContentPOIPending(contentID);
    }

    public void addContentToPOI(int poiId, String name, String desc, File f) {
        contentController.addContentToPOI(poiId, name, desc, f);
    }

    public void confirmAddContent() {
        contentController.confirmAddContent();
    }

    public void confirmAddContentPending() {
        contentController.confirmAddContentPending();
    }

    public void deletePOI() {
        this.comune.deletePOI(this.viewController.getLastViewedPoi().getId());
    }

    public void deleteItinerary() {
        this.comune.deleteItinerary(this.viewController.getLastViewedItinerary().getId());
    }

    public void deleteContent() {
        this.comune.deleteContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public List<POIGI> getAllPendingContentPOI() {
        return comune.getAllPendingContentPOI();
    }

    public ContentFD selectedPendingContent(int i) {
        return viewController.selectedPendingContent(i);
    }

    public void deletePendingContent() {
        comune.deletePendingContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public void validateSelectedContent() {
        comune.validateSelectedContent(this.viewController.getLastViewedPoi().getId(), this.viewController.getLastViewedContent().getId());
    }

    public List<ItineraryGI> getAllPendingItinerary() {
        return comune.getAllPendingItinerary();
    }

    public ItineraryFD selectedPendingItinerary(int i) {
        return viewController.selectedPendingItinerary(i);
    }

    public void validateSelectedItinerary() {
        comune.validateSelectedItinerary(this.viewController.getLastViewedItinerary().getId());
    }

    public void deletePendingItinerary() {
        comune.deletePendingItinerary(this.viewController.getLastViewedItinerary().getId());
    }
}
