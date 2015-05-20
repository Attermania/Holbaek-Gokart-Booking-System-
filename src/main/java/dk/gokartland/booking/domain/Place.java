package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Place {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private boolean canBeMultiBooked;

    public Place(String name, boolean canBeMultiBooked) {
        this.name = name;
        this.canBeMultiBooked = canBeMultiBooked;
    }

    protected Place() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean CanBeMultiBooked() {
        return canBeMultiBooked;
    }

    public boolean isSamePlace(BookablePlace bookablePlace) {

        for(Place place : bookablePlace.getPlaces()) {
            if(this.getId() == place.getId()) return true;
        }

        return false;
    }
}
