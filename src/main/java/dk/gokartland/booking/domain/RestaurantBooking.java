package dk.gokartland.booking.domain;

import java.util.Date;

/**
 * Created by Thomas on 06-05-2015.
 */
public class RestaurantBooking extends FacilityBooking {

        public RestaurantBooking(Date from, Date to, String comments, short numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }
}
