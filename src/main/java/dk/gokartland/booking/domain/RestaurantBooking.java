package dk.gokartland.booking.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn
public class RestaurantBooking extends FacilityBooking {

    public RestaurantBooking(Date from, Date to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }

    protected RestaurantBooking() {
    }
}