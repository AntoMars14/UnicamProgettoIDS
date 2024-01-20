package it.unicam.model;
import it.unicam.model.util.POIFD;
import java.util.ArrayList;
import java.util.List;

public class Comune {

    private Coordinates coordinates;
    private String name;
    private List<POI> POIValidate = new ArrayList<>();
    private List<POI> POIPending = new ArrayList<>();

    private POIFD lastViewedPOI;

    public Comune(String name, Coordinates coord) {
        this.name = name;
        this.coordinates = coord;
        POILuogo comune = new POILuogo(coord);
        comune.insertPOIInfo(name, "Questo è il comune di Camerino");
        this.insertPOI(comune);
        this.lastViewedPOI = null;
    }

    public void insertPOI(POI p) {
        this.POIValidate.add(p);
        p.setPOIId(this.POIValidate.indexOf(p) + 1);
    }

    public void insertPOIPending(POI p) {
        this.POIPending.add(p);
        p.setPOIId(this.POIValidate.indexOf(p) + 1);
    }

    public List<POI> getPOIValidate() {
        return POIValidate;
    }

    public List<POI> getPOIPending() {
        return POIPending;

    }
}
