package it.unicam.model;

public class POIEventoFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinates c) {
        return new POIEvento(c);
    }
}
