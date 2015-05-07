package dk.gokartland.booking.domain;

import java.util.Date;

/**
 * Created by Thomas on 06-05-2015.
 */
public class RestaurantBooking extends FacilityBooking {

        public RestaurantBooking(Date from, Date to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }
}
