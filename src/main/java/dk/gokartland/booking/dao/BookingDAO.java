package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.domain.FacilityBooking;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingDAO {

	private EntityManagerFactory entityManagerFactory;

	public BookingDAO(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<FacilityBooking> getFacilityBookingsWithin(Calendar from, Calendar to) {

		List<FacilityBooking> facilityBookings = new ArrayList<>();

		return facilityBookings;
	}

	public boolean save(Booking booking) {

		return true;
	}

	public boolean delete(FacilityBooking facilityBooking) {
		return true;
	}
}
