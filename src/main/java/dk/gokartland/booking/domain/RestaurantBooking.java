package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Calendar;

@Entity
@PrimaryKeyJoinColumn
public class RestaurantBooking extends FacilityBooking {

    @Column
    private int numberOfPeople;

    public RestaurantBooking(Calendar from, Calendar to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, bookablePlace);
        this.numberOfPeople = numberOfPeople;
    }

    protected RestaurantBooking() {
    }

    @Override
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void changeNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}