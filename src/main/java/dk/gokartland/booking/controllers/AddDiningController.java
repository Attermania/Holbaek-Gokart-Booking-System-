package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.LasertagBooking;
import dk.gokartland.booking.domain.Place;
import dk.gokartland.booking.domain.RestaurantBooking;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;
import dk.gokartland.booking.services.BookingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Thomas on 06-05-2015.
 */
public class AddDiningController extends Observable implements Initializable {

    private BookingService bookingService;

    @FXML
    Button addButton;

    @FXML
    ComboBox<Integer> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextField noOfPeopleTextField;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    public AddDiningController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }

        for (int i = 0; i <= 55; i += 5) {
            minutes.add(i);
        }

        fromHourComboBox.setItems(hours);
        fromMinuteComboBox.setItems(minutes);
        toHourComboBox.setItems(hours);
        toMinuteComboBox.setItems(minutes);


        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate fromDate = fromDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHourComboBox.getValue(), fromMinuteComboBox.getValue());
                Calendar calendarTo = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), toHourComboBox.getValue(), toMinuteComboBox.getValue());

                int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());


                List<Place> places = new ArrayList<Place>();
                places.add(new Place("test", false));
                BookablePlace bookablePlace = new BookablePlace("test", places);


                // Create Lasertag Booking
                try {
                    RestaurantBooking restaurantBooking = bookingService.createRestaurantBooking(calendarFrom,
                            calendarTo,
                            noOfPeopleTextField.getText(),
                            noOfPeople,
                            bookablePlace);

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(restaurantBooking);
                    clearChanged();

                    // Close window


                } catch (PlaceAlreadyBookedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
