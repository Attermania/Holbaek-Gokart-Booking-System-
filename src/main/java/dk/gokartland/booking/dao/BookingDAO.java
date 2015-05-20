package dk.gokartland.booking.dao;

import dk.gokartland.booking.domain.*;
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

        TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb WHERE fb.from >= :from AND fb.to <= :to", FacilityBooking.class);
        //from.add(1, -1);
        query.setParameter("from", from, TemporalType.TIMESTAMP);
        query.setParameter("to", to, TemporalType.TIMESTAMP);

        return query.getResultList();
    }

    public List<FacilityBooking> getCollidingFacilityBookings(Calendar from, Calendar to) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb WHERE (fb.from < :to AND fb.from > :from ) OR (fb.to > :from AND fb.to < :to)", FacilityBooking.class);

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

    public boolean update(Booking booking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(booking);
        entityManager.flush();

        transaction.commit();

        return true;
    }


    public boolean updateFacilityBooking(FacilityBooking facilityBooking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(facilityBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }



    public boolean deleteFacilityBooking(FacilityBooking detachedFacilityBooking) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            FacilityBooking facilityBooking = entityManager.find(FacilityBooking.class, detachedFacilityBooking.getId());
            entityManager.remove(facilityBooking);
            entityManager.flush();

            transaction.commit();
        } catch(IllegalArgumentException e) {}

        return true;
    }

    public boolean delete(Booking booking) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Booking entity = entityManager.find(Booking.class, booking.getId());

        entityManager.remove(entity);
        entityManager.flush();

        transaction.commit();

        return true;
    }
}