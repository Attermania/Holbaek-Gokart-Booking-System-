package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.services.BookingService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class AddGokartController implements Initializable {

    private BookingService bookingService;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    ComboBox fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox, placeComboBox;

    @FXML
    TextField adultCartsTextField, childrenCartsTextField;

    @FXML
    CheckBox champagneCheckBox, medalsCheckBox;

    @FXML
    Button addButton;

    public AddGokartController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LocalDate fromDate = fromDatePicker.getValue();


        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Booking booking = bookingService.createGokartBooking(
//
//                );
            }

            Calendar calendar = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
        });


    }
}
