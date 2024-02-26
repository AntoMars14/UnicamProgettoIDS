package it.unicam.model.util.dtos;

import it.unicam.model.Coordinates;
import it.unicam.model.Type;

public class POIGI {
    private final Long id;
    private final String name;
    private final String description;
    private final Coordinates coordinates;
    private final Type type;

    public POIGI(Long id, String name, String description, Coordinates coord, Type type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coord;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }


    @Override
    public String toString() {
        return  "Id: "+this.id+" Name= " + name +
                "\nDescription=" + description +
                "\n Coordinates= lon " + coordinates.getLat() + " lon " +coordinates.getLon()+
                "\n Type=" + type;
    }
}
