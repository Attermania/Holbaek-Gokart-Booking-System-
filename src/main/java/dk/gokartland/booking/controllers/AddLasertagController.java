package dk.gokartland.booking.controllers;

import dk.gokartland.booking.services.BookingService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dion on 05-05-2015.
 */
public class AddLasertagController implements Initializable {

    private BookingService bookingService;

    public AddLasertagController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
