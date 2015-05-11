package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.GokartBooking;
import dk.gokartland.booking.domain.PaintballBooking;
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
import sun.swing.BakedArrayList;

import java.awt.print.Book;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddPaintballController extends Observable implements Initializable {

    private BookingService bookingService;

    @FXML
    Button addButton;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<Integer> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextArea commentTextArea;

    @FXML
    TextField noOfPeopleTextField;

    public AddPaintballController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer> hours = FXCollections.observableArrayList();
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        for(int i = 0; i < 24; i++) {
            hours.add(i);
        }

        for(int i = 0; i <= 55; i += 5) {
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
                places.add(new Place(1, "test", false));
                BookablePlace bookablePlace = new BookablePlace(1, "test", places);

                try {
                    // Create Paintball Booking
                    PaintballBooking paintballBooking = bookingService.createPaintballBooking(
                            calendarFrom,
                            calendarTo,
                            commentTextArea.getText(), noOfPeople, bookablePlace);

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(paintballBooking);
                    clearChanged();

                    // Close window

                } catch (PlaceAlreadyBookedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(countObservers());

        }
    }
