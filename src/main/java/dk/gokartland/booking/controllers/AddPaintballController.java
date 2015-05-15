package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.swing.BakedArrayList;

import java.awt.print.Book;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddPaintballController extends Observable implements Initializable {

    private BookingService bookingService;
    private BookablePlaceDAO bookablePlaceDAO;

    @FXML
    AnchorPane root;

    @FXML
    Button addButton;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<String> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextArea commentTextArea;

    @FXML
    TextField noOfPeopleTextField;

    @FXML
    Label errorLabel;

    public AddPaintballController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
    }

    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();

        for (int i = 24; i > 0; i--) {

            String hour = "" + i;
            if (hour.length() < 2) {
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

        Integer fromHour = Integer.parseInt(fromHourComboBox.getValue());
        Integer fromMinute = Integer.parseInt(fromMinuteComboBox.getValue());
        Integer toHour = Integer.parseInt(toHourComboBox.getValue());
        Integer toMinute = Integer.parseInt(toMinuteComboBox.getValue());

        placeComboBox.setItems(FXCollections.observableArrayList(bookablePlaceDAO.getAll()));

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate fromDate = fromDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHour, fromMinute);
                Calendar calendarTo = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), toMinute, toMinute);

                try {

                int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());
                    // Create Paintball Booking
                    PaintballBooking paintballBooking = bookingService.createPaintballBooking(
                            calendarFrom,
                            calendarTo,
                            commentTextArea.getText(), noOfPeople, placeComboBox.getValue());

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(paintballBooking);
                    clearChanged();

                    // Close window
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.close();

                } catch (PlaceAlreadyBookedException e) {
                    errorLabel.setText("Banen er allerede booket");
                } catch (NumberFormatException nfe) {
                    errorLabel.setText("Kontroller at alle felter er korrekt udfyldt");
                }
            }
        });

        System.out.println(countObservers());
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

        fromHourComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
            }
        });

        toHourComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
            }
        });

        fromMinuteComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
            }
        });

        toMinuteComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeDateAndClock();
            }
        });

    }

    private void setDateAndClock() {
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        fromHourComboBox.setValue("12");
        fromMinuteComboBox.setValue("00");
        toHourComboBox.setValue("12");
        toMinuteComboBox.setValue("30");
    }

    private void changeDateAndClock() {

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();


        Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), Integer.parseInt(fromHourComboBox.getValue()), Integer.parseInt(fromMinuteComboBox.getValue()));
        Calendar calendarTo = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), Integer.parseInt(toHourComboBox.getValue()), Integer.parseInt(toMinuteComboBox.getValue()));

        if (calendarTo.before(calendarFrom)) {
            toDatePicker.setValue(fromDatePicker.getValue());
        } else if (calendarTo.equals(calendarFrom)) {
            calendarTo.add(Calendar.HOUR_OF_DAY, 1);

            for(String time : toHourComboBox.getItems()) {
                int hour = calendarTo.get(Calendar.HOUR_OF_DAY);
                String sHour = hour < 10 ? "0" + hour : hour + "";

                if(time.equals(sHour)) {
                    // Select the current string
                    toHourComboBox.getSelectionModel().select(time);
                    break;
                }
            }

        }
    }


}
