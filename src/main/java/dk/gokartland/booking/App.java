package dk.gokartland.booking;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.factories.FXMLFactory;
import dk.gokartland.booking.services.BookingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.*;

public class App extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		Place place1 = new Place("Bane 1", false);
		Place place2 = new Place("Bane 2", false);
		Place place3 = new Place("Paintball/Lasertag", false);
		Place place4 = new Place("Restaurant", true);

		BookablePlace bookablePlace1 = new BookablePlace("Bane 1", Arrays.asList(place1));
		BookablePlace bookablePlace2 = new BookablePlace("Bane 2", Arrays.asList(place2));
		BookablePlace bookablePlace3 = new BookablePlace("Stor bane", Arrays.asList(place1, place2));
		BookablePlace bookablePlace4 = new BookablePlace("Paintball/Lasertag", Arrays.asList(place3));
		BookablePlace bookablePlace5 = new BookablePlace("Restaurant", Arrays.asList(place4));

        Calendar from = new GregorianCalendar();
        Calendar to = new GregorianCalendar();

        GokartBooking gokartBooking = new GokartBooking(from, to, "Test", 1, 2, bookablePlace1, true, true);

        List<FacilityBooking> facilityBookingList = new ArrayList<>();
        facilityBookingList.add(gokartBooking);

        Booking booking1 = new Booking("John", "123456", true, false, "John@gmail.com", "Test", "JFK", facilityBookingList);

		EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(place1);
		entityManager.persist(place2);
		entityManager.persist(place3);
		entityManager.persist(place4);

		entityManager.persist(bookablePlace1);
		entityManager.persist(bookablePlace2);
		entityManager.persist(bookablePlace3);
		entityManager.persist(bookablePlace4);
		entityManager.persist(bookablePlace5);

        entityManager.persist(booking1);

		entityManager.flush();

		entityTransaction.commit();

		FXMLFactory fxmlFactory = applicationContext.getBean(FXMLFactory.class);

		Stage stage = fxmlFactory.build(getClass().getResource("main.fxml"));
		stage.show();
	}
}
