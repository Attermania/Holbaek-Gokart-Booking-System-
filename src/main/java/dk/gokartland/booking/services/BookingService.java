package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.domain.exceptions.MissingInformationException;
import dk.gokartland.booking.domain.exceptions.NoFacilityBookingsException;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;


import java.util.Calendar;
import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public Booking createBooking(String customerName, String phoneNumber, boolean isPrivateClient, boolean needsPermission, String email, String comments, String createdBy, boolean isPaid, String referenceNumber, List<FacilityBooking> facilityBookings) throws NoFacilityBookingsException, MissingInformationException {

        Booking booking = new Booking(customerName, phoneNumber, isPrivateClient, needsPermission, email, comments, createdBy, isPaid, referenceNumber,  facilityBookings);

        for(FacilityBooking facilityBooking : facilityBookings) {
            facilityBooking.setBooking(booking);
        }

        if(facilityBookings.size() == 0) throw new NoFacilityBookingsException();

        boolean persisted = bookingDAO.save(booking);

        if (persisted) return booking;

        return null;
    }

    public boolean updateBooking(Booking booking) {
        return bookingDAO.update(booking);
    }

    public boolean updateGokartBooking(GokartBooking gokartBooking){
        return bookingDAO.updateFacilityBooking(gokartBooking);
    }

    public boolean updatePaintballBooking(PaintballBooking paintballBooking){
        return bookingDAO.updateFacilityBooking(paintballBooking);
    }

    public boolean updateLasertagBooking(LasertagBooking lasertagBooking) {
        return bookingDAO.updateFacilityBooking(lasertagBooking);
    }

    public boolean updateRestaurantBooking(RestaurantBooking restaurantBooking){
        return bookingDAO.updateFacilityBooking(restaurantBooking);
    }

    public GokartBooking createGokartBooking(Calendar from, Calendar to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) throws PlaceAlreadyBookedException {

        if(!checkIfPlaceIsAvailable(bookablePlace, from, to)) throw new PlaceAlreadyBookedException();

        return new GokartBooking(from, to, comments, adultCarts, childrenCarts, bookablePlace, champagne, medals);
    }

    public PaintballBooking createPaintballBooking(Calendar from, Calendar to, String comments, int antal, BookablePlace bookablePlace) throws PlaceAlreadyBookedException {

        if(!checkIfPlaceIsAvailable(bookablePlace, from, to)) throw new PlaceAlreadyBookedException();

        return new PaintballBooking(from, to, comments, antal, bookablePlace);
    }

    public LasertagBooking createLasertagBooking(Calendar from, Calendar to, String comments, int antal, BookablePlace bookablePlace) throws PlaceAlreadyBookedException {

        if(!checkIfPlaceIsAvailable(bookablePlace, from, to)) throw new PlaceAlreadyBookedException();

        return new LasertagBooking(from, to, comments, antal, bookablePlace);
    }

    public RestaurantBooking createRestaurantBooking(Calendar from, Calendar to, String comments, int antal, BookablePlace bookablePlace) throws PlaceAlreadyBookedException {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, from, to)) throw new PlaceAlreadyBookedException();

        return new RestaurantBooking(from, to, comments, antal, bookablePlace);
    }

    public boolean deleteFacilityBooking(FacilityBooking facilityBooking){

        return bookingDAO.deleteFacilityBooking(facilityBooking);
    }

    public boolean deleteBooking(Booking booking) {

        return bookingDAO.delete(booking);
    }

    private boolean checkIfPlaceIsAvailable(BookablePlace bookablePlace, Calendar from, Calendar to) {

        List<FacilityBooking> existingFacilityBookings = bookingDAO.getCollidingFacilityBookings(from, to);

        for(FacilityBooking facilityBooking : existingFacilityBookings) {
            if( facilityBooking.isSamePlace(bookablePlace) ) return false;
        }

        return true;
    }

}
