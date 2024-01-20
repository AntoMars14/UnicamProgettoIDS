package it.unicam.model;

public class POILuogoFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinates c) {
        return new POILuogo(c);
    }
}
