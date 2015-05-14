package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
import dk.gokartland.booking.domain.FacilityBooking;
import dk.gokartland.booking.domain.GokartBooking;
import dk.gokartland.booking.services.BookingService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 14-05-2015.
 */
public class UpdateBookingController implements Initializable {

    @FXML
    Button saveButton;

    @FXML
    Label updateLabel;

    private BookingService bookingService;
    private BookablePlaceDAO bookablePlaceDAO;


    public UpdateBookingController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Test");
            }
        });

    }


}
