package dk.gokartland.booking.domain;

/**
 * Created by Thomas on 06-05-2015.
 */
public class Place {

    private int id;
    private String name;
    private boolean canBeMultiBooked;

    public Place(int id, String name, boolean canBeMultiBooked) {
        this.id = id;
        this.name = name;
        this.canBeMultiBooked = canBeMultiBooked;
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
            if(this == place) return true;
        }

        return false;
    }
}
