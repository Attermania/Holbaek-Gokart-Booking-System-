package dk.gokartland.booking.controllers;

import dk.gokartland.booking.services.BookingService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 06-05-2015.
 */
public class AddGokartController  implements Initializable{

    private BookingService bookingService;

    public AddGokartController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
