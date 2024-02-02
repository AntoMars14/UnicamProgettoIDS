package it.unicam.model;

import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;

import java.util.ArrayList;
import java.util.List;

public abstract class POI {

    private int POIId;
    private String name;
    private String description;
    private Type type;
    private final Coordinates coord;

    private List<Content> contents;
    private List<Content> contentsPending;

    public POI(Coordinates coord) {
        if(coord == null) throw new NullPointerException("Coordinate null");
        this.coord = coord;
        this.contents = new ArrayList<>();
        this.contentsPending = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public Coordinates getCoord() {
        return coord;
    }
    public  List<Content> getContents(){
        return this.contents;
    };

    public List<Content> getContentsPending() {
        return contentsPending;
    }

    public void addContent(Content c){
        if(c == null) throw new NullPointerException("Contenuto null");
        this.contents.add(c);
    }

    public void addContentPending(Content c){
        if(c == null) throw new NullPointerException("Contenuto null");
        this.contentsPending.add(c);
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Type type){
        this.type = type;
    }

    public int getPOIId() {
        return POIId;
    }

    public void setPOIId(int POIId) {
        this.POIId = POIId;
    }

    public abstract void insertPOIInfo(String name, String description);

    public abstract POIGI getPOIGeneralInfo();

    public abstract POIFD getFullDetailedPOI();

    public void deleteContent(int contentId) {
        this.contents.remove(contentId - 1);
        this.contents.stream().forEach(content -> content.setContentId(this.contents.indexOf(content)+1));
    }
}
