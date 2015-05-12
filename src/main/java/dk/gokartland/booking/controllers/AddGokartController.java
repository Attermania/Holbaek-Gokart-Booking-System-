package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.Booking;
import dk.gokartland.booking.domain.GokartBooking;
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

public class AddGokartController extends Observable implements Initializable {

    private BookingService bookingService;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<Integer> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextField adultCartsTextField, childrenCartsTextField;

    @FXML
    TextArea commentTextArea;

    @FXML
    CheckBox champagneCheckBox, medalsCheckBox;

    @FXML
    Button addButton;


    public AddGokartController(BookingService bookingService) {
        this.bookingService = bookingService;
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

        for(int i = 0; i <= 55; i += 5) {
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

                int adultCarts = Integer.parseInt(adultCartsTextField.getText());
                int childrenCarts = Integer.parseInt(childrenCartsTextField.getText());

                List<Place> places = new ArrayList<Place>();
                places.add(new Place(1, "test", false));
                BookablePlace bookablePlace = new BookablePlace(1, "test", places);

                try {
                    // Create Gokart Booking
                    GokartBooking gokartBooking = bookingService.createGokartBooking(
							calendarFrom,
							calendarTo,
							commentTextArea.getText(),
							adultCarts,
							childrenCarts,
							bookablePlace, ///////////// SLIMED - Change it!
							champagneCheckBox.isSelected(),
							medalsCheckBox.isSelected()
					);

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(gokartBooking);
                    clearChanged();

                    // Close window


                } catch (PlaceAlreadyBookedException e) {
                    e.printStackTrace();
                }

            }
        });

        System.out.println(countObservers());

    }

    private void setDateAndClock() {
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        fromHourComboBox.setValue(12);
        fromMinuteComboBox.setValue(00);
        toHourComboBox.setValue(12);
        toMinuteComboBox.setValue(30);
    }
}
