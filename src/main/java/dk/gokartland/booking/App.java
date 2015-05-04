package dk.gokartland.booking;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.factories.FXMLFactory;
import dk.gokartland.booking.services.BookingService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		FXMLFactory fxmlFactory = applicationContext.getBean(FXMLFactory.class);

		Stage stage = fxmlFactory.build(getClass().getResource("main.fxml"));
		stage.show();
	}
}
