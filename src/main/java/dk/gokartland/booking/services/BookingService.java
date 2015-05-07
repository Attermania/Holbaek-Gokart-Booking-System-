package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO;

    private EntityManagerFactory entityManagerFactory;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public GokartBooking createGokartBooking(Date from, Date to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) throws Exception {

        List<FacilityBooking> facilityBookingsWithinRange = bookingDAO.getFacilityBookingsWithin(from, to);

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new Exception("Place is not available at the given time");

        return new GokartBooking(from, to, comments, adultCarts, childrenCarts, bookablePlace, champagne, medals);
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
