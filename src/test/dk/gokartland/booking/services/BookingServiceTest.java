package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.FacilityBooking;
import dk.gokartland.booking.domain.Place;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;

public class BookingServiceTest {

	@Test(expected=PlaceAlreadyBookedException.class)
	public void testThrowsExceptionWhenPlaceIsAlreadyBooked() throws Exception {

		// Mocks
		BookingDAO bookingDAO = mock(BookingDAO.class);
		FacilityBooking facilityBooking = mock(FacilityBooking.class);

		ArrayList<FacilityBooking> facilityBookings = new ArrayList<>();
		facilityBookings.add(facilityBooking);

		// Mock expectations - What is the real word?
		when(bookingDAO.getFacilityBookingsWithin(any(), any())).thenReturn(facilityBookings);
		when(facilityBooking.isSamePlace(any())).thenReturn(true);

		BookingService bookingService = new BookingService(bookingDAO);

//		bookingService.createGokartBooking(new Date(), new Date(), "", 5, 5, any(), true, true);
	}
}
