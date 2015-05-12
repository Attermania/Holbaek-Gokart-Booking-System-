package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
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
    private BookablePlaceDAO bookablePlaceDAO;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<String> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextField adultCartsTextField, childrenCartsTextField;

    @FXML
    TextArea commentTextArea;

    @FXML
    CheckBox champagneCheckBox, medalsCheckBox;

    @FXML
    Button addButton;


    public AddGokartController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();

        for(int i = 24; i > 0; i--) {

            String hour = "" + i;
            if(hour.length() < 2) {
                hour = "0" + i;
            }
            hours.add(hour);
        }

        for(int i = 55; i >= 0; i -= 5) {

            String minute = "" +i;
            if(minute.length() < 2){
                minute = "0" +i;
            }
            minutes.add(minute);
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


                Integer fromHour = Integer.parseInt(fromHourComboBox.getValue());
                Integer fromMinute = Integer.parseInt(fromMinuteComboBox.getValue());
                Integer toHour = Integer.parseInt(toHourComboBox.getValue());
                Integer toMinute = Integer.parseInt(toMinuteComboBox.getValue());


                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHour, fromMinute);
                Calendar calendarTo = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), toMinute, toMinute);

                int adultCarts = Integer.parseInt(adultCartsTextField.getText());
                int childrenCarts = Integer.parseInt(childrenCartsTextField.getText());

                try {
                    // Create Gokart Booking
                    GokartBooking gokartBooking = bookingService.createGokartBooking(
							calendarFrom,
							calendarTo,
							commentTextArea.getText(),
							adultCarts,
							childrenCarts,
							placeComboBox.getValue(),
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
        fromHourComboBox.setValue("12");
        fromMinuteComboBox.setValue("00");
        toHourComboBox.setValue("12");
        toMinuteComboBox.setValue("30");
    }
}
