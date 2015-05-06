package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.Date;

public abstract class FacilityBooking {

	private int id;

	private Date from;

	private Date to;

	private String comments;

	private short numberOfPeople;

	public FacilityBooking(Date from, Date to, String comments, short numberOfPeople) {
		this.from = from;
		this.to = to;
		this.comments = comments;
		this.numberOfPeople = numberOfPeople;
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

	public short getNumberOfPeople() {
		return numberOfPeople;
	}
}
