package dk.gokartland.booking.services;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.Booking;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

public class BookingService {

	private BookingDAO bookingDAO;

	private EntityManagerFactory entityManagerFactory;

	public BookingService(BookingDAO bookingDAO, EntityManagerFactory entityManagerFactory) {
		this.bookingDAO = bookingDAO;
		this.entityManagerFactory = entityManagerFactory;
	}

	public Booking create(String name) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		Booking booking = new Booking(name);

		transaction.begin();

		entityManager.persist(booking);
		entityManager.flush();

		transaction.commit();

		return booking;
	}

}
