package dk.gokartland.booking.domain;

import java.util.Date;

/**
 * Created by Thomas on 06-05-2015.
 */
public class PaintballBooking extends FacilityBooking {

    public PaintballBooking(Date from, Date to, String comments, short numberOfPeople) {
        super(from, to, comments, numberOfPeople);
    }
}
