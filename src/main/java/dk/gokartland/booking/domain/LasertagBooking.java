package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Calendar;

@Entity
@PrimaryKeyJoinColumn
public class LasertagBooking extends FacilityBooking {

    @Column
    private int numberOfPeople;

    public LasertagBooking(Calendar from, Calendar to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, bookablePlace);
        this.numberOfPeople = numberOfPeople;
    }

    protected LasertagBooking() {
    }

    @Override
    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}
