package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
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
    private BookablePlaceDAO bookablePlaceDAO;

    @FXML
    Button addButton;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<Integer> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextField noOfPeopleTextField;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    public AddDiningController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        for(int i = 24; i > 0; i--) {

            String hour = "" + i;
            if(hour.length() < 2) {
                hour = "0" + i;
            }
            Integer x = Integer.parseInt(hour);
            hours.add(x);
        }
        

        for (int i = 0; i <= 55; i += 5) {
            minutes.add(i);
        }

        fromHourComboBox.setItems(hours);
        fromMinuteComboBox.setItems(minutes);
        toHourComboBox.setItems(hours);
        toMinuteComboBox.setItems(minutes);

        setDateAndClock();

        placeComboBox.setItems(FXCollections.observableArrayList(bookablePlaceDAO.getAll()));
        
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate fromDate = fromDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHourComboBox.getValue(), fromMinuteComboBox.getValue());
                Calendar calendarTo = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), toHourComboBox.getValue(), toMinuteComboBox.getValue());

                int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());


                // Create Lasertag Booking
                try {
                    RestaurantBooking restaurantBooking = bookingService.createRestaurantBooking(calendarFrom,
                            calendarTo,
                            noOfPeopleTextField.getText(),
                            noOfPeople,
                            placeComboBox.getValue());

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

    private void setDateAndClock(){
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        fromHourComboBox.setValue(12);
        fromMinuteComboBox.setValue(00);
        toHourComboBox.setValue(12);
        toMinuteComboBox.setValue(30);
    }
}
