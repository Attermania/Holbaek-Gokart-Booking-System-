package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.GokartBooking;
import dk.gokartland.booking.domain.LasertagBooking;
import dk.gokartland.booking.domain.Place;
import dk.gokartland.booking.domain.exceptions.PlaceAlreadyBookedException;
import dk.gokartland.booking.services.BookingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Dion on 05-05-2015.
 */
public class AddLasertagController extends Observable implements Initializable {

    private BookingService bookingService;

    @FXML
    Button addButton;

    @FXML
    ComboBox<Integer> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    TextField noOfPeopleTextField;

    @FXML
    TextArea commentTextArea;


    public AddLasertagController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

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

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate fromDate = fromDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHourComboBox.getValue(), fromMinuteComboBox.getValue());
                Calendar calendarTo = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), toHourComboBox.getValue(), toMinuteComboBox.getValue());

                int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());


                List<Place> places = new ArrayList<Place>();
                places.add(new Place(1, "test", false));
                BookablePlace bookablePlace = new BookablePlace(1, "test", places);


                // Create Lasertag Booking
                try {
                    LasertagBooking lasertagBookingBooking = bookingService.createLasertagBooking(calendarFrom,
                            calendarTo,
                            noOfPeopleTextField.getText(),
                            noOfPeople,
                            bookablePlace);

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(lasertagBookingBooking);
                    clearChanged();

                    // Close window


                } catch (PlaceAlreadyBookedException e) {
                    e.printStackTrace();
                }
            }
        });

        fromDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
            }
        });

        toDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
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

    private void changeDateAndClock() {

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHourComboBox.getValue(), fromMinuteComboBox.getValue());
        Calendar calendarTo = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), toHourComboBox.getValue(), toMinuteComboBox.getValue());

        if (calendarTo.before(calendarFrom)) {
            toDatePicker.setValue(fromDatePicker.getValue());
        } else if (calendarTo.equals(calendarFrom)) {
            toHourComboBox.setValue(fromHourComboBox.getValue()+1);
        }
    }

}
