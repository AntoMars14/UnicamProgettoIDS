package it.unicam.model;

public class POILuogoConOraFactory extends POIFactory{
    @Override
    public POI createPOI(Coordinates c) {
        return new POILuogoConOra(c);
    }
}
