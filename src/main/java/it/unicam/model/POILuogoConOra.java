package it.unicam.model;

import it.unicam.model.util.ContentGI;
import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class POILuogoConOra extends POI{

    private LocalTime[] openingTime = new LocalTime[7];
    private LocalTime[] closingTime = new LocalTime[7];

    public POILuogoConOra(Coordinates coord) {
        super(coord);
        this.setType(Type.LUOGOCONORA);
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

    public LocalTime[] getOpeningTime() {
        return openingTime;
    }

    public LocalTime[] getClosingTime() {
        return closingTime;
    }

    public void insertTime(LocalTime[] openingTime, LocalTime[] closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
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
    public void setPOIId(int POIId) {
        super.setPOIId(POIId);
    }

    @Override
    public void insertPOIInfo(String name, String description) {
        if(name == null || description == null) throw new NullPointerException("Info null");
        this.setName(name);
        this.setDescription(description);
    }

    @Override
    public POIGI getPOIGeneralInfo() {
        return new POIGI(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getType());
    }

    @Override
    public POIFD getFullDetailedPOI() {
        List<ContentGI> contentsGI = new ArrayList<>();
        for (Content c: this.getContents()) {
            contentsGI.add(c.getContentGeneralInfo());
        }
        return new POIFD(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getType(), this.openingTime, this.closingTime, contentsGI);
    }
}
