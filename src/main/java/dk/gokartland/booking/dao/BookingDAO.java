package dk.gokartland.booking.dao;

import javax.persistence.EntityManagerFactory;

public class BookingDAO {

	private EntityManagerFactory entityManagerFactory;

	public BookingDAO(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

}
