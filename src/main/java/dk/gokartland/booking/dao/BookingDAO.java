package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.domain.FacilityBooking;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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

		TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb WHERE fb.from >= :from OR fb.to <= :to", FacilityBooking.class);
        query.setParameter("from", from, TemporalType.TIMESTAMP);
        query.setParameter("to", to, TemporalType.TIMESTAMP);

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
