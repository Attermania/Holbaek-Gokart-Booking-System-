package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;

import java.util.Date;
import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public GokartBooking createGokartBooking(Date from, Date to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) throws Exception {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new PlaceAlreadyBookedException();

        return new GokartBooking(from, to, comments, adultCarts, childrenCarts, bookablePlace, champagne, medals);
    }

    public Booking createBooking(String customerName, String phoneNumber, boolean isBusiness, boolean needsPermission, String email, String comments, String createdBy, List<FacilityBooking> facilityBookings) {

        Booking booking = new Booking(customerName, phoneNumber, isBusiness, needsPermission, email, comments, createdBy, facilityBookings);

        boolean persisted = bookingDAO.save(booking);

        if (persisted) return booking;

        return null;
    }

    public PaintballBooking createPaintballBooking(Date from, Date to, String comments, int antal, BookablePlace bookablePlace) throws Exception {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new Exception("Place is not available at the given time");

        return new PaintballBooking(from, to, comments, antal, bookablePlace);
    }

    public LasertagBooking createLasertagBooking(Date from, Date to, String comments, int antal, BookablePlace bookablePlace) throws Exception {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new Exception("Place is not available at the given time");

        return new LasertagBooking(from, to, comments, antal, bookablePlace);
    }

    public RestaurantBooking createRestaurantBooking(Date from, Date to, String comments, int antal, BookablePlace bookablePlace) throws Exception {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new Exception("Place is not available at the given time");

        return new RestaurantBooking(from, to, comments, antal, bookablePlace);
    }

    public boolean deleteFacilityBooking(FacilityBooking facilityBooking){

        return bookingDAO.delete(facilityBooking);
    }

    private boolean checkIfPlaceIsAvailable(BookablePlace bookablePlace, List<FacilityBooking> existingFacilityBookings) {

        for(FacilityBooking facilityBooking : existingFacilityBookings) {
            if( facilityBooking.isSamePlace(bookablePlace) ) return true;
        }

        return false;
    }

}
