package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.BookablePlace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BookablePlaceDAO {

	private EntityManagerFactory entityManagerFactory;

	public BookablePlaceDAO(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<BookablePlace> getAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		return entityManager.createQuery("SELECT bp FROM BookablePlace bp", BookablePlace.class).getResultList();
	}

}
