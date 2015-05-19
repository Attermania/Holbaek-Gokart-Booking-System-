package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
import dk.gokartland.booking.domain.*;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DiningController extends Observable implements Initializable, EditableController {

    private BookingService bookingService;
    private BookablePlaceDAO bookablePlaceDAO;

    @FXML
    AnchorPane root;

    @FXML
    Button addButton;

    @FXML
    ComboBox<BookablePlace> placeComboBox;

    @FXML
    ComboBox<String> fromHourComboBox, fromMinuteComboBox, toHourComboBox, toMinuteComboBox;

    @FXML
    TextField noOfPeopleTextField;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    @FXML
    Label errorLabel, titleLabel;

    @FXML
    GridPane bottomGrid;

    @FXML
    TextArea commentTextArea;

    public DiningController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
    }

    @Override
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
                LocalDate toDate = toDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHour, fromMinute);
                Calendar calendarTo = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), toHour, toMinute);

                // Create Lasertag Booking
                try {

                int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());
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
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.close();

                } catch (PlaceAlreadyBookedException e) {
                    errorLabel.setText("Banen er allerede booket");
                } catch (NumberFormatException nfe) {
                    errorLabel.setText("Kontroller at alle felter er korrekt udfyldt");
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

    @Override
    public void setupForEdit(FacilityBooking facilityBooking) {
        Stage stage = (Stage) root.getScene().getWindow();

        if( !(facilityBooking instanceof RestaurantBooking) ) stage.close();

        RestaurantBooking restaurantBooking = (RestaurantBooking) facilityBooking;

        titleLabel.setText("Restaurantbooking #" + restaurantBooking.getId());

        Calendar from = restaurantBooking.getFrom();
        Calendar to = restaurantBooking.getTo();

        fromDatePicker.setValue(LocalDate.of(from.get(Calendar.YEAR), from.get(Calendar.MONTH)+1, from.get(Calendar.DAY_OF_MONTH)));
        toDatePicker.setValue(LocalDate.of(to.get(Calendar.YEAR), to.get(Calendar.MONTH)+1, to.get(Calendar.DAY_OF_MONTH)));

        fromHourComboBox.setValue(String.valueOf(from.get(Calendar.HOUR_OF_DAY)));
        fromMinuteComboBox.setValue(String.valueOf(from.get(Calendar.MINUTE)));
        toHourComboBox.setValue(String.valueOf(to.get(Calendar.HOUR_OF_DAY)));
        toMinuteComboBox.setValue(String.valueOf(to.get(Calendar.MINUTE)));

        placeComboBox.setValue(restaurantBooking.getBookablePlace());
        noOfPeopleTextField.setText(String.valueOf(restaurantBooking.getNumberOfPeople()));
        commentTextArea.setText(restaurantBooking.getComments());

        Button updateButton = new Button("Gem");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer fromHour = Integer.parseInt(fromHourComboBox.getValue());
                Integer fromMinute = Integer.parseInt(fromMinuteComboBox.getValue());
                Integer toHour = Integer.parseInt(toHourComboBox.getValue());
                Integer toMinute = Integer.parseInt(toMinuteComboBox.getValue());

                LocalDate fromDate = fromDatePicker.getValue();
                LocalDate toDate = toDatePicker.getValue();

                Calendar calendarFrom = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), fromHour, fromMinute);
                Calendar calendarTo = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), toHour, toMinute);

                // Create Lasertag Booking
                try {

                    int noOfPeople = Integer.parseInt(noOfPeopleTextField.getText());



                    restaurantBooking.changeFrom(calendarFrom);
                    restaurantBooking.changeTo(calendarTo);
                    restaurantBooking.changeBookablePlace(placeComboBox.getValue());
                    restaurantBooking.changeComments(commentTextArea.getText());
                    if(restaurantBooking instanceof RestaurantBooking) ((RestaurantBooking) restaurantBooking).changeNumberOfPeople(noOfPeople);

                    // Insert bookingService updateMethod and use restaurantBooking below
                    bookingService.updateRestaurantBooking(restaurantBooking);

                    // Observer pattern notify booking window
                    setChanged();
                    notifyObservers(restaurantBooking);
                    clearChanged();


                    // Close window
                    stage.close();

                //} catch (PlaceAlreadyBookedException e) {
                //    errorLabel.setText("Banen er allerede booket");
                } catch (NumberFormatException nfe) {
                    errorLabel.setText("Kontroller at alle felter er korrekt udfyldt");
                }
            }
        });

        bottomGrid.getChildren().remove(addButton);
        bottomGrid.add(updateButton, 0, 1);


    }
}
