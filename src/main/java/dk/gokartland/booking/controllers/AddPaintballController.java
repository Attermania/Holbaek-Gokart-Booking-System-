package dk.gokartland.booking.controllers;

import dk.gokartland.booking.services.BookingService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPaintballController implements Initializable {

    private BookingService bookingService;

    public AddPaintballController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
