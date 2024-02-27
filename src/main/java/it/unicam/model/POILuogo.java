package it.unicam.model;

import it.unicam.model.util.dtos.ContentGI;
import it.unicam.model.util.dtos.POIFD;
import it.unicam.model.util.dtos.POIGI;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class POILuogo extends  POI{

    public POILuogo(Coordinates coord) {
        super(coord);
        this.setType(Type.LUOGO);
    }

    public POILuogo() {
        super();
    }

    @Override
    public Long getPOIId() {
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
        List<ContentGI> contentsGI = this.getContents().stream().map(c -> c.getContentGeneralInfo()).toList();
        List<ContentGI> pendingContentsGI = this.getContentsPending().stream().map(pc -> pc.getContentGeneralInfo()).toList();
        return new POIFD(this.getPOIId(), this.getName(), this.getDescription(), this.getCoord(), this.getType(), contentsGI, pendingContentsGI);
    }
}
