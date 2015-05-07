package dk.gokartland.booking.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn
public class LasertagBooking extends FacilityBooking {

    public LasertagBooking(Date from, Date to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, numberOfPeople, bookablePlace);
    }

    protected LasertagBooking() {
    }
}
