package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BookablePlace {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @ManyToMany
    private List<Place> places = new ArrayList<>();

    public BookablePlace(String name, List<Place> places) {
        this.name = name;
        this.places = places;
    }

    protected BookablePlace() {
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

    @Override
    public String toString() {
        return getName();
    }
}
