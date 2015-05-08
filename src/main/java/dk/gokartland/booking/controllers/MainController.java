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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private Button newBookingButton, resetButton;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn;

    @FXML
    ComboBox<String> typeSearchComboBox;

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
        newBookingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("createBooking.fxml"));
                stage.show();
            }
        });

        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Insert logic
            }
        });

        ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();

        List<Place> places = new ArrayList<>();
        places.add(new Place(1, "Bane 1", false));
        Place actionbane = new Place(2, "Action bane", false);
        places.add(actionbane);


        GokartBooking gokartBooking = new GokartBooking(new GregorianCalendar(), new GregorianCalendar(), "Comment", 5, 5, new BookablePlace(1, "Bane 1", places), true, true);
        PaintballBooking paintballBooking = new PaintballBooking(new GregorianCalendar(), new GregorianCalendar(), "Comment", (short) 5, new BookablePlace(1, "Bane 2", places));

        facilityBookings.addAll(gokartBooking, paintballBooking);


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
//                DateFormat dateFormat = new SimpleDateFormat();
//                Calendar date = facilityBooking.getValue().getFrom();
//                String currentTime = dateFormat.format(date);
//
//                return new SimpleStringProperty(currentTime);
                return new SimpleStringProperty("");
            }
        });

        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
//                DateFormat dateFormat = new SimpleDateFormat();
//                Calendar date = facilityBooking.getValue().getTo();
//                String currentTime = dateFormat.format(date);
//
//                return new SimpleStringProperty(currentTime);
//                return new SimpleStringProperty("--");
                return new SimpleStringProperty("");
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
                            if (facilityBooking instanceof GokartBooking){
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Paintball":
                        for(FacilityBooking facilityBooking : facilityBookings) {
                            if(facilityBooking instanceof PaintballBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Lasertag":
                        for(FacilityBooking facilityBooking : facilityBookings) {
                            if(facilityBooking instanceof LasertagBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;
                    case "Restaurant":
                        for(FacilityBooking facilityBooking : facilityBookings) {
                            if(facilityBooking instanceof RestaurantBooking) {
                                tempList.addAll(facilityBooking);
                                facilityBookingTableView.setItems(tempList);
                            }
                        }
                        break;

                }


            }
        });


    }
}
