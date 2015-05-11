package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.domain.FacilityBooking;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.transaction.Transaction;
import java.util.Calendar;
import java.util.List;

public class BookingDAO {

	private EntityManagerFactory entityManagerFactory;

	public BookingDAO(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<FacilityBooking> getFacilityBookingsWithin(Calendar from, Calendar to) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb", FacilityBooking.class);

		return query.getResultList();
	}

	public boolean save(Booking booking) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.persist(booking);
		entityManager.flush();

		transaction.commit();

		return true;
	}

	public boolean delete(FacilityBooking facilityBooking) {
		return true;
	}
}
