package dk.gokartland.booking.domain;

import java.util.ArrayList;
import java.util.List;

public class BookablePlace {

    private int id;
    private String name;

    private List<Place> places = new ArrayList<>();

    public BookablePlace(int id, String name, List<Place> places) {
        this.id = id;
        this.name = name;
        this.places = places;
    }

    public void addPlace (Place place) {
        if(!places.contains(place)) {
            places.add(place);
        }
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public List<Place> getPlaces() {
        return places;
    }


	public boolean isSamePlace(BookablePlace bookablePlace) {

		for(Place place : places) {

			if( place.isSamePlace(bookablePlace) ) return true;

		}

		return false;
	}
}
