package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookablePlaceDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.factories.FXMLFactory;
import dk.gokartland.booking.services.BookingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Thomas on 14-05-2015.
 */
public class UpdateBookingController extends Observable implements Initializable, Observer {

    @FXML
    AnchorPane root;

    @FXML
    TextField nameTextField, phoneTextField, emailTextField, createdByTextField;

    @FXML
    Button addGokartButton, addPaintballButton, addLasertagButton, addDiningButton, saveButton;

    @FXML
    RadioButton privateRadioButton;

    @FXML
    CheckBox needsPermissionCheckBox;

    @FXML
    TextArea commentsTextArea;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn;

    private BookingService bookingService;
    private BookablePlaceDAO bookablePlaceDAO;
    private FXMLFactory fxmlFactory;

    private ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();


    public UpdateBookingController(BookingService bookingService, BookablePlaceDAO bookablePlaceDAO, FXMLFactory fxmlFactory) {
        this.bookingService = bookingService;
        this.bookablePlaceDAO = bookablePlaceDAO;
        this.fxmlFactory = fxmlFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateBookingController self = this;

        addGokartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("addGokart.fxml"), self);

                try {
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        addPaintballButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("addPaintball.fxml"), self);

                try {
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        addLasertagButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("addLasertag.fxml"), self);

                try {
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addDiningButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("addDining.fxml"), self);

                try {
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
                Calendar fromDate = facilityBooking.getValue().getFrom();
                return new SimpleStringProperty(fromDate.get(Calendar.DAY_OF_MONTH) + "/" + fromDate.get(Calendar.MONTH) + "-" + fromDate.get(Calendar.YEAR) + " " + fromDate.get(Calendar.HOUR_OF_DAY) + ":" + fromDate.get(Calendar.MINUTE));
            }
        });
        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                Calendar toDate = facilityBooking.getValue().getTo();
                return new SimpleStringProperty(toDate.get(Calendar.DAY_OF_MONTH) + "/" + toDate.get(Calendar.MONTH) + "-" + toDate.get(Calendar.YEAR) + " " + toDate.get(Calendar.HOUR_OF_DAY) + ":" + toDate.get(Calendar.MINUTE));
            }
        });

        // Opens specific booking window
        facilityBookingTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(facilityBookingTableView.getSelectionModel().getSelectedItem());
                    FXMLLoader loader = fxmlFactory.build(getClass().getResource("addDining.fxml"));

                    try {
                        Parent root = loader.load();

                        Scene scene = new Scene(root);

                        Stage stage = new Stage();
                        stage.setScene(scene);

                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Test");
            }
        });

    }

    public void setup(Booking booking) {

        nameTextField.setText(booking.getCustomerName());
        phoneTextField.setText(booking.getPhoneNumber());
        emailTextField.setText(booking.getEmail());
        privateRadioButton.selectedProperty().setValue(booking.isPrivateClient());
        needsPermissionCheckBox.selectedProperty().setValue(booking.isNeedsPermission());
        commentsTextArea.setText(booking.getComments());
        createdByTextField.setText(booking.getCreatedBy());

        for(FacilityBooking facilityBooking : booking.getFacilityBookings()) {
            facilityBookings.add(facilityBooking);
            facilityBookingTableView.setItems(facilityBookings);

        }
    }

    @Override
    public void update(Observable o, Object obj) {
        if (obj instanceof FacilityBooking) {
            FacilityBooking facilityBooking = (FacilityBooking) obj;

            facilityBookings.add(facilityBooking);
            facilityBookingTableView.setItems(facilityBookings);
        }

    }
}
