package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.factories.FXMLFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable, Observer {

    @FXML
    private Button newBookingButton, resetButton;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn;

    @FXML
    ComboBox<String> typeSearchComboBox;

    @FXML
    DatePicker fromDatePicker, toDatePicker;

    String typeGokart = "Gokart";
    String typePaintBall = "Paintball";
    String typeLasertag = "Lasertag";
    String typeDining = "Restaurant";

    ObservableList<String> types = FXCollections.observableArrayList();

    private BookingDAO bookingDAO;
    private FXMLFactory fxmlFactory;

    public MainController(BookingDAO bookingDAO, FXMLFactory fxmlFactory) {
        this.bookingDAO = bookingDAO;
        this.fxmlFactory = fxmlFactory;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Observer self = this;

        newBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("createBooking.fxml"), self);
                stage.show();
            }
        });

        Calendar from = new GregorianCalendar();
        Calendar to = new GregorianCalendar();
        to.add(Calendar.HOUR, 1);
        ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList(bookingDAO.getFacilityBookingsWithin(from, to));

        facilityBookingTableView.setItems(facilityBookings);

        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                String type = "";

                if (facilityBooking.getValue() instanceof GokartBooking) type = "Gokart";
                else if (facilityBooking.getValue() instanceof PaintballBooking) type = "Paintball";
                else if (facilityBooking.getValue() instanceof LasertagBooking) type = "Lasertag";
                else if (facilityBooking.getValue() instanceof RestaurantBooking) type = "Restaurant";
                return new SimpleStringProperty(type);
            }
        });

        placeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                return new SimpleStringProperty(facilityBooking.getValue().getBookablePlace().getName());
            }
        });

        fromColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                String fromDate = facilityBooking.getValue().getFrom().getTime().toString();
                return new SimpleStringProperty(fromDate);
            }
        });

        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                String toDate = facilityBooking.getValue().getFrom().getTime().toString();
                return new SimpleStringProperty(toDate);
            }
        });

        // Opens specific booking window
        facilityBookingTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(facilityBookingTableView.getSelectionModel().getSelectedItem());
                    Stage stage = fxmlFactory.build(getClass().getResource("createBooking.fxml"));
                    stage.show();
                }
            }
        });

        typeSearchComboBox.setItems(types);
        types.addAll(typeGokart, typePaintBall, typeLasertag, typeDining);

        typeSearchComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ObservableList<FacilityBooking> tempList = FXCollections.observableArrayList();

                switch (typeSearchComboBox.getSelectionModel().getSelectedItem()) {
                    case "Gokart":
                        for (FacilityBooking facilityBooking : facilityBookings) {
                            if (facilityBooking instanceof GokartBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Paintball":
                        for (FacilityBooking facilityBooking : facilityBookings) {
                            if (facilityBooking instanceof PaintballBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Lasertag":
                        for (FacilityBooking facilityBooking : facilityBookings) {
                            if (facilityBooking instanceof LasertagBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Restaurant":
                        for (FacilityBooking facilityBooking : facilityBookings) {
                            if (facilityBooking instanceof RestaurantBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;

                }


            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<FacilityBooking> tempList = FXCollections.observableArrayList();
                tempList.addAll(facilityBookings);
                facilityBookingTableView.setItems(tempList);
                fromDatePicker.setValue(LocalDate.now());
                toDatePicker.setValue(LocalDate.now());

            }
        });

        fromDatePicker.setValue(LocalDate.now());
        fromDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
            }
        });

        toDatePicker.setValue(LocalDate.now());
        toDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search();
            }
        });


    }

    private void search() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        ObservableList<FacilityBooking> tempList = FXCollections.observableArrayList();

        Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
        Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());


        for (FacilityBooking facilityBooking : bookingDAO.getFacilityBookingsWithin(from, to)) {

            tempList.addAll(facilityBooking);
        }
        facilityBookingTableView.setItems(tempList);
    }

    @Override
    public void update(Observable o, Object obj) {

        if(obj instanceof Booking) {
            fillList();
        }
    }

    public void fillList() {
        ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth());
        Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());

        for(FacilityBooking facilityBooking : bookingDAO.getFacilityBookingsWithin(from, to)) {
            facilityBookings.add(facilityBooking);
        }
        facilityBookingTableView.setItems(facilityBookings);
    }


}
