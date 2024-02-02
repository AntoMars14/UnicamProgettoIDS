package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
import it.unicam.model.Content;
import it.unicam.model.Itinerary;
import it.unicam.model.POI;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ItineraryFD;
import it.unicam.model.util.POIFD;

public class ViewController {
    private POIFD lastViewedPOI;
    private ContentFD lastViewedContent;
    private ItineraryFD lastViewedItinerary;
    private Comune comune;


    public ViewController(Comune comune) {
        this.comune = comune;
    }


    public POIFD getLastViewedPoi() {
        return lastViewedPOI;
    }

    public ContentFD getLastViewedContent() {
        return lastViewedContent;
    }

    public ItineraryFD getLastViewedItinerary() {
        return lastViewedItinerary;
    }

    public POIFD viewSelectedPOI(int POIId) {
        lastViewedPOI = comune.viewSelectedPOI(POIId);
        return lastViewedPOI;
    }

    public ContentFD viewContent(int contentID) {
        this.lastViewedContent = comune.viewContent(this.lastViewedPOI.getId(), contentID);
        return this.lastViewedContent;
    }

    public ContentFD viewContentPending(int contentID){
        return this.comune.viewContentPending(this.lastViewedPOI.getId(), contentID);
    }

    public ItineraryFD selectedItinerary(int i) {
        this.lastViewedItinerary = this.comune.selectedItinerary(i);
        return this.lastViewedItinerary;
    }

    public POIFD selectedPendingPOI(int i) {
        this.lastViewedPOI = this.comune.selectedPendingPOI(i);
        return this.lastViewedPOI;
    }

}
