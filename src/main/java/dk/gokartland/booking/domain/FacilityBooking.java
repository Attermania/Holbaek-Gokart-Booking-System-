package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class FacilityBooking {

	@Id
	private int id;

	@Column
	private Date from;

	@Column
	private Date to;

	@Column(length = 2000)
	private String comments;

	@Column
	private int numberOfPeople;

    private BookablePlace bookablePlace;

	public FacilityBooking(Date from, Date to, String comments, int numberOfPeople, BookablePlace bookablePlace) {
		this.from = from;
		this.to = to;
		this.comments = comments;
		this.numberOfPeople = numberOfPeople;
        this.bookablePlace = bookablePlace;
    }

	protected FacilityBooking() {
	}

	public int getId() {
		return id;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public String getComments() {
		return comments;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

    public BookablePlace getBookablePlace() {
        return bookablePlace;
    }

    public boolean isSamePlace(BookablePlace bookablePlace) {
		return bookablePlace.isSamePlace(bookablePlace);
	}
}
