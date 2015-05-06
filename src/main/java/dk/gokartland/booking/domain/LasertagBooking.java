package dk.gokartland.booking.domain;

import java.util.Date;

/**
 * Created by Dion on 06-05-2015.
 */
public class LasertagBooking extends FacilityBooking {

    public LasertagBooking(Date from, Date to, String comments, short numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }
}
