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

        //TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb WHERE fb.from >= :from AND fb.to <= :to", FacilityBooking.class);
        TypedQuery<FacilityBooking> query = entityManager.createQuery("SELECT fb FROM FacilityBooking fb WHERE fb.from >= :from AND fb.to <= :to", FacilityBooking.class);
        //from.add(1, -1);
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

    public boolean updateGokartBooking(GokartBooking gokartBooking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(gokartBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }

    public boolean updatePaintBallBooking(PaintballBooking paintballBooking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(paintballBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }

    public boolean updateLasertagBooking(LasertagBooking lasertagBooking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(lasertagBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }

    public boolean updateRestaurantBooking(RestaurantBooking restaurantBooking) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(restaurantBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }



    public boolean deleteFacilityBooking(FacilityBooking facilityBooking) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(facilityBooking);
        entityManager.flush();

        transaction.commit();

        return true;
    }

    public boolean delete(Booking booking) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(booking);
        entityManager.flush();

        transaction.commit();

        return true;
    }
}