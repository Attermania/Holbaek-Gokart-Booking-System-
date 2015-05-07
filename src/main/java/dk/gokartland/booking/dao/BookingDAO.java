package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.FacilityBooking;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO {

	private EntityManagerFactory entityManagerFactory;

	public BookingDAO(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<FacilityBooking> getFacilityBookingsWithin(Date from, Date to) {

		List<FacilityBooking> facilityBookings = new ArrayList<>();

		return facilityBookings;
	}

	public boolean delete(FacilityBooking facilityBooking) {
		return true;
	}
}
