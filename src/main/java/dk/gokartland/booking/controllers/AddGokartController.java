package dk.gokartland.booking.controllers;

import dk.gokartland.booking.services.BookingService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 06-05-2015.
 */
public class AddGokartController implements Initializable {

    private BookingService bookingService;

    @FXML
    Button addButton;

    public AddGokartController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Insert logic
            }
        });


    }
}
