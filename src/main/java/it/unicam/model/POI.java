package it.unicam.model;

import it.unicam.model.util.POIFD;
import it.unicam.model.util.POIGI;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class POI {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poi_generator")
    private Long POIId;
    private String name;
    private String description;
    private Type type;
    @Embedded
    private Coordinates coord;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Content> contents;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Content> contentsPending;

    public POI() {
        this.contents = new ArrayList<>();
        this.contentsPending = new ArrayList<>();
    }
    public POI(String name, String description) {
        if(name == null || description == null) throw new NullPointerException("Parametri null");
        this.name = name;
        this.description = description;
        this.type = null;
        this.coord = null;
        this.contents = new ArrayList<>();
        this.contentsPending = new ArrayList<>();
    }
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


    public Long getPOIId() {
        return POIId;
    }

    public Long setPOIId(Long POIId) {
        return this.POIId = POIId;
    }

    public abstract void insertPOIInfo(String name, String description);

    public abstract POIGI getPOIGeneralInfo();

    public abstract POIFD getFullDetailedPOI();

    public void deleteContent(int contentId) {
        this.contents.remove(contentId - 1);
        this.contents.stream().forEach(content -> content.setContentId(this.contents.indexOf(content)+1));
    }

    public void validateContent(int id){
        this.contents.add(this.contentsPending.get(id-1));
        this.contentsPending.get(id-1).setContentId(this.contents.size());
        this.deletePendingContent(id);
    }

    public void deletePendingContent(int id) {
        this.contentsPending.remove(id-1);
        this.contentsPending.stream().forEach(c -> c.setContentId(this.contentsPending.indexOf(c)+1));
    }
}
