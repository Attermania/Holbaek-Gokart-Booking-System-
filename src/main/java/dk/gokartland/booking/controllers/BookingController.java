package dk.gokartland.booking.controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 05-05-2015.
 */
public class BookingController implements Initializable {

    @FXML
    Button addGokartButton, addPaintballButton, addLasertagButton, addDiningButton, createButton;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn;

    private FXMLFactory fxmlFactory;

    public BookingController(FXMLFactory fxmlFactory) {
        this.fxmlFactory = fxmlFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addGokartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("addGokart.fxml"));
                stage.show();
            }
        });

        addPaintballButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("addPaintball.fxml"));
                stage.show();
            }
        });

        addLasertagButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("addLasertag.fxml"));
                stage.show();
            }
        });

        addDiningButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = fxmlFactory.build(getClass().getResource("addDining.fxml"));
                stage.show();
            }
        });

        createButton.setOnAction(new EventHandler<ActionEvent>() {
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

    }
}
