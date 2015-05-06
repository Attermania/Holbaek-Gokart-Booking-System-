package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.FacilityBooking;
import dk.gokartland.booking.domain.Place;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingServiceTest {

	@Test(expected=IndexOutOfBoundsException.class)
	public void testFailsWhenTryingToCreateBookingAtAnAlreadyBookedPlace() throws Exception {

		BookingDAO bookingDAO = Mockito.mock(BookingDAO.class);

		Date date1 = new Date();
		Date date2 = new Date(System.currentTimeMillis() + 10000);

		Mockito.when(bookingDAO.getFacilityBookingsWithin(date1, date2)).thenReturn(new ArrayList<FacilityBooking>());

		BookingService bookingService = new BookingService(bookingDAO);

		List<Place> places = new ArrayList<>();
		places.add(new Place(1, "Bane 1", false));
		places.add(new Place(2, "Bane 2", false));

		BookablePlace bookablePlace = new BookablePlace(1, "Stor bane", places);

		bookingService.createGokartBooking(date1, date2, "", 5, 5, bookablePlace, true, true);
	}
}
