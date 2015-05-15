package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Calendar;

@Entity
@PrimaryKeyJoinColumn
public class PaintballBooking extends FacilityBooking {

    @Column
    private int numberOfPeople;

    public PaintballBooking(Calendar from, Calendar to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
        super(from, to, comments, bookablePlace);
        this.numberOfPeople = numberOfPeople;
    }

    protected PaintballBooking() {
    }

    @Override
    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}
