package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.domain.FacilityBooking;
import dk.gokartland.booking.domain.GokartBooking;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;
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

        if(!checkIfPlaceIsAvailable(bookablePlace, facilityBookingsWithinRange)) throw new PlaceAlreadyBookedException();

        return new GokartBooking(from, to, comments, adultCarts, childrenCarts, bookablePlace, champagne, medals);
    }

    public Booking createBooking(String customerName, String phoneNumber, boolean isBusiness, boolean needsPermission, String email, String comments, String createdBy, List<FacilityBooking> facilityBookings) {

        Booking booking = new Booking(customerName, phoneNumber, isBusiness, needsPermission, email, comments, createdBy, facilityBookings);

        boolean persisted = bookingDAO.save(booking);

        if(persisted) return booking;

        return null;
    }

    private boolean checkIfPlaceIsAvailable(BookablePlace bookablePlace, List<FacilityBooking> existingFacilityBookings) {

        for(FacilityBooking facilityBooking : existingFacilityBookings) {
            if( facilityBooking.isSamePlace(bookablePlace) ) return true;
        }

        return false;
    }

}