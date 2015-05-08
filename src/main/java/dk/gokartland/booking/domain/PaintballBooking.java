package dk.gokartland.booking.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Calendar;

@Entity
@PrimaryKeyJoinColumn
public class PaintballBooking extends FacilityBooking {

    public PaintballBooking(Calendar from, Calendar to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }

    protected PaintballBooking() {
    }
}
