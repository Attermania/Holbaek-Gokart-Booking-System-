package dk.gokartland.booking;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.factories.FXMLFactory;
import dk.gokartland.booking.services.BookingService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

		entityManager.flush();

		entityTransaction.commit();

		BookingService bookingService = applicationContext.getBean(BookingService.class);


		Calendar from = new GregorianCalendar();
		Calendar to = new GregorianCalendar();
		Calendar from1 = new GregorianCalendar();
		from1.add(10, 47);
		Calendar to1 = new GregorianCalendar();
		to1.add(10, 48);

		GokartBooking gokartBooking = new GokartBooking(from, to, "Test", 1, 2, bookablePlace1, true, true);
		PaintballBooking paintballBooking = new PaintballBooking(from1, to1, "Test", 2, bookablePlace4);

		List<FacilityBooking> gokartlist = new ArrayList<>();
		gokartlist.add(gokartBooking);

		List<FacilityBooking> paintballlist = new ArrayList<>();
		paintballlist.add(paintballBooking);

		Booking booking1 = bookingService.createBooking("John", "123456", true, false, "John@gmail.com", "Test", "JFK", gokartlist);
		bookingService.createBooking("Jane", "123", true, false, "mail", "Test", "JFK", paintballlist);

		// Make window
		FXMLFactory fxmlFactory = applicationContext.getBean(FXMLFactory.class);

		FXMLLoader loader = fxmlFactory.build(getClass().getResource("main.fxml"));

		Parent root = loader.load();

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
