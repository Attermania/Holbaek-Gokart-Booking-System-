package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BookablePlace {

    @Id
    private int id;

    @Column
    private String name;

    @ManyToMany(targetEntity = Place.class)
    private List<Place> places = new ArrayList<>();

    public BookablePlace(int id, String name, List<Place> places) {
        this.id = id;
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
