package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class FacilityBooking {

	@Id
    @GeneratedValue
	private int id;

	@Column(name = "\"from\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar from;

	@Column(name = "\"to\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar to;

	@Column(length = 2000)
	private String comments;

	@ManyToOne
    private BookablePlace bookablePlace;

	@ManyToOne
	private Booking booking;

	public FacilityBooking(Calendar from, Calendar to, String comments, BookablePlace bookablePlace) {
		this.from = from;
		this.to = to;
		this.comments = comments;
        this.bookablePlace = bookablePlace;
    }

	protected FacilityBooking() {
	}

	public int getId() {
		return id;
	}

	public Calendar getFrom() {
		return from;
	}

	public Calendar getTo() {
		return to;
	}

	public String getComments() {
		return comments;
	}

    public BookablePlace getBookablePlace() {
        return bookablePlace;
    }

    public boolean isSamePlace(BookablePlace bookablePlace) {
		return this.bookablePlace.isSamePlace(bookablePlace);
	}

	public abstract int getNumberOfPeople();

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Booking getBooking() {
		return booking;
	}

    public void changeFrom(Calendar from) {
        this.from = from;
    }

    public void changeTo(Calendar to) {
        this.to = to;
    }

    public void changeComments(String comments) {
        this.comments = comments;
    }

    public void changeBookablePlace(BookablePlace bookablePlace) {
        this.bookablePlace = bookablePlace;
    }


}
