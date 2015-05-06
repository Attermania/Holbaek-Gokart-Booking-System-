package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.factories.FXMLFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	@FXML
	private Button newBookingButton, resetButton;

	private BookingDAO bookingDAO;
	private FXMLFactory fxmlFactory;

	public MainController(BookingDAO bookingDAO, FXMLFactory fxmlFactory) {
		this.bookingDAO = bookingDAO;
		this.fxmlFactory = fxmlFactory;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		newBookingButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = fxmlFactory.build(getClass().getResource("createBooking.fxml"));
				stage.show();
			}
		});

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Insert logic
            }
        });
	}
}
