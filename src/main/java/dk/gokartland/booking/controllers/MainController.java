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

    private String typeAll = "Alle";
    private String typeGokart = "Gokart";
    private String typePaintBall = "Paintball";
    private String typeLasertag = "Lasertag";
    private String typeDining = "Restaurant";

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
        from.add(Calendar.HOUR, -1);
        Calendar to = new GregorianCalendar();
        to.add(Calendar.HOUR, 24);
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
                    Stage stage = fxmlFactory.build(getClass().getResource("updateBooking.fxml"));
                    stage.show();
                }
            }
        });

        typeSearchComboBox.setItems(types);
        types.addAll(typeAll, typeGokart, typePaintBall, typeLasertag, typeDining);
        typeSearchComboBox.getSelectionModel().select(typeAll);

        typeSearchComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                search();

            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fromDatePicker.setValue(LocalDate.now());
                toDatePicker.setValue(LocalDate.now());

                LocalDate fromDate = fromDatePicker.getValue();
                LocalDate toDate = toDatePicker.getValue();

                Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth());
                from.add(Calendar.HOUR, -1);
                Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth());
                to.add(Calendar.HOUR, 12);

                ObservableList<FacilityBooking> tempList = FXCollections.observableArrayList(bookingDAO.getFacilityBookingsWithin(from, to));
                facilityBookingTableView.setItems(tempList);


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

        Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), 0, 0, 0);
        Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), 23, 59, 59);

        List<FacilityBooking> facilityBookings = bookingDAO.getFacilityBookingsWithin(from, to);

        ObservableList<FacilityBooking> tempList = FXCollections.observableArrayList();

        switch (typeSearchComboBox.getSelectionModel().getSelectedItem()) {
            case "Gokart":
                for (FacilityBooking facilityBooking : facilityBookings) {
                    if (facilityBooking instanceof GokartBooking) {
                        tempList.add(facilityBooking);
                    }
                }
                break;
            case "Paintball":
                for (FacilityBooking facilityBooking : facilityBookings) {
                    if (facilityBooking instanceof PaintballBooking) {
                        tempList.add(facilityBooking);
                    }
                }
                break;
            case "Lasertag":
                for (FacilityBooking facilityBooking : facilityBookings) {
                    if (facilityBooking instanceof LasertagBooking) {
                        tempList.add(facilityBooking);
                    }
                }
                break;
            case "Restaurant":
                for (FacilityBooking facilityBooking : facilityBookings) {
                    if (facilityBooking instanceof RestaurantBooking) {
                        tempList.add(facilityBooking);
                    }
                }
                break;
            default:
                for(FacilityBooking facilityBooking : facilityBookings) {
                    tempList.add(facilityBooking);
                }
        }

        facilityBookingTableView.setItems(tempList);
    }

    @Override
    public void update(Observable o, Object obj) {

        if (obj instanceof Booking) {
            fillList();
        }
    }

    public void fillList() {
        ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();

        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth());
        Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth());
        System.out.println(from.getTime().toString());
        from.add(Calendar.HOUR, -1);
        to.add(Calendar.HOUR, 24);

        for (FacilityBooking facilityBooking : bookingDAO.getFacilityBookingsWithin(from, to)) {
            facilityBookings.add(facilityBooking);
        }
        facilityBookingTableView.setItems(facilityBookings);
    }


}
