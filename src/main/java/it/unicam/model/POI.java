package it.unicam.model;

import it.unicam.model.util.dtos.POIFD;
import it.unicam.model.util.dtos.POIGI;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public abstract void insertPOIInfo(String name, String description);

    public abstract POIGI getPOIGeneralInfo();

    public abstract POIFD getFullDetailedPOI();

    public void deleteContent(Long contentId) {
        this.contents.removeIf(c -> c.getContentId().equals(contentId));
    }

    public void validateContent(Long id){
        this.contents.add(this.contentsPending.stream().filter(c -> c.getContentId().equals(id)).findFirst().get());
        this.deletePendingContent(id);
    }

    public void deletePendingContent(Long id) {
        this.contentsPending.removeIf(c -> c.getContentId().equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof POI)) return false;
        POI poi = (POI) o;
        return getPOIId().equals(poi.getPOIId());
    }
}
