package it.unicam.model;

import it.unicam.model.util.ContentGI;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class POIEvento extends POI{

    private LocalDateTime openingDate;
    private LocalDateTime closingDate;

    public POIEvento(Coordinates coord) {
        super(coord);
        this.setType(Type.EVENTO);
    }

    @Override
    public int getPOIId() {
        return super.getPOIId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Coordinates getCoord() {
        return super.getCoord();
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void insertDate(LocalDateTime openingDate, LocalDateTime closingDate) {
        this.openingDate = openingDate;
        this.closingDate = closingDate;
    }

    @Override
    public void setPOIId(int POIId) {
        super.setPOIId(POIId);
    }

    @Override
    public void setType(Type type) {
        super.setType(type);
    }

    @Override
    public List<Content> getContents() {
        return super.getContents();
    }

    @Override
    public void addContent(Content c) {
        super.addContent(c);
    }

    @Override
    public void insertPOIInfo(String name, String description){
        if(name == null || description == null) throw new NullPointerException("Info null");
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public POIGI getPOIGeneralInfo(){
        return new POIGI(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getType());
    }

    @Override
    public POIFD getFullDetailedPOI(){
        List<ContentGI> contentsGI = new ArrayList<>();
        for (Content c: this.getContents()) {
            contentsGI.add(c.getContentGeneralInfo());
        }
        return new POIFD(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getType(), this.openingDate, this.closingDate, contentsGI);
    }
}