package it.unicam.model.controllersGRASP;

import it.unicam.model.Comune;
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

    public ContentFD viewContentPOIPending(int contentID){
        this.lastViewedContent = this.comune.viewContentPOIPending(this.lastViewedPOI.getId(), contentID);
        return lastViewedContent;
    }

    public ItineraryFD selectedItinerary(int i) {
        this.lastViewedItinerary = this.comune.selectedItinerary(i);
        return this.lastViewedItinerary;
    }

    public POIFD selectedPendingPOI(int i) {
        this.lastViewedPOI = this.comune.selectedPendingPOI(i);
        return this.lastViewedPOI;
    }

    public ContentFD selectedPendingContent(int contentId) {
        this.lastViewedContent = comune.selectedPendingContent(this.lastViewedPOI.getId(), contentId);
        return this.lastViewedContent;
    }

    public ItineraryFD selectedPendingItinerary(int i) {
        lastViewedItinerary = comune.selectedPendingItinerary(i);
        return lastViewedItinerary;
    }
}
