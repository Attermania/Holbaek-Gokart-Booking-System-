package dk.gokartland.booking.controllers;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BookingController extends Observable implements Initializable, Observer {

    @FXML
    AnchorPane root;

    @FXML
    TextField nameTextField, phoneTextField, emailTextField, createdByTextField;

    @FXML
    Button addGokartButton, addPaintballButton, addLasertagButton, addDiningButton, createButton;

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

    @FXML
    Label titleLabel;

    @FXML
    GridPane mainGrid;

    private FXMLFactory fxmlFactory;
    private BookingService bookingService;

    private ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();

    public BookingController(FXMLFactory fxmlFactory, BookingService bookingService) {
        this.fxmlFactory = fxmlFactory;
        this.bookingService = bookingService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BookingController self = this;

        addGokartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("gokart.fxml"), self);

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
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("paintball.fxml"), self);

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
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("lasertag.fxml"), self);

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
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("dining.fxml"), self);

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

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isPrivateClient = privateRadioButton.isSelected();

                Booking booking = bookingService.createBooking(nameTextField.getText(), phoneTextField.getText(), isPrivateClient, needsPermissionCheckBox.isSelected(), emailTextField.getText(), commentsTextArea.getText(), createdByTextField.getText(), facilityBookings);

                setChanged();
                notifyObservers(booking);
                clearChanged();

                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();
            }
        });

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
                Calendar fromDate = facilityBooking.getValue().getFrom();
                return new SimpleStringProperty(fromDate.get(Calendar.DAY_OF_MONTH) + "-" + fromDate.get(Calendar.MONTH) + "-" + fromDate.get(Calendar.YEAR) + " " + fromDate.get(Calendar.HOUR_OF_DAY) + ":" + fromDate.get(Calendar.MINUTE));
            }
        });

        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                Calendar toDate = facilityBooking.getValue().getTo();
                return new SimpleStringProperty(toDate.get(Calendar.DAY_OF_MONTH) + "-" + toDate.get(Calendar.MONTH) + "-" + toDate.get(Calendar.YEAR) + " " + toDate.get(Calendar.HOUR_OF_DAY) + ":" + toDate.get(Calendar.MINUTE));
            }
        });

        facilityBookingTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                    FacilityBooking facilityBooking = facilityBookingTableView.getSelectionModel().getSelectedItem();
                    FXMLLoader loader;

                    if (facilityBooking instanceof GokartBooking)
                        loader = fxmlFactory.build(getClass().getResource("gokart.fxml"));
                    else if(facilityBooking instanceof PaintballBooking)
                        loader = fxmlFactory.build(getClass().getResource("paintball.fxml"));
                    else if(facilityBooking instanceof LasertagBooking)
                        loader = fxmlFactory.build(getClass().getResource("lasertag.fxml"));
                    else
                        loader = fxmlFactory.build(getClass().getResource("addRestaurant.fxml"));

                    try {
                        Parent root = loader.load();
                        EditableController controller = loader.getController();


                        Scene scene = new Scene(root);

                        Stage stage = new Stage();
                        stage.setScene(scene);

                        controller.setupForEdit(facilityBookingTableView.getSelectionModel().getSelectedItem());

                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    @Override
    public void update(Observable o, Object obj) {
        if (obj instanceof FacilityBooking) {
            FacilityBooking facilityBooking = (FacilityBooking) obj;

            facilityBookings.add(facilityBooking);
            facilityBookingTableView.setItems(facilityBookings);
        }
    }

    public void setupForEdit(Booking booking) {

        titleLabel.setText("Booking #" + booking.getId());

        Button updateButton = new Button("Gem");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                booking.changeCustomerName(nameTextField.getText());

                for(FacilityBooking facilityBooking : facilityBookings) {
                    booking.addFacilityBooking(facilityBooking);
                }

                bookingService.updateBooking(booking);
            }
        });

        mainGrid.getChildren().remove(createButton);
        mainGrid.add(updateButton, 1, 11);

        nameTextField.setText(booking.getCustomerName());
        phoneTextField.setText(booking.getPhoneNumber());
        emailTextField.setText(booking.getEmail());
        privateRadioButton.selectedProperty().setValue(booking.isPrivateClient());
        needsPermissionCheckBox.selectedProperty().setValue(booking.isNeedsPermission());
        commentsTextArea.setText(booking.getComments());
        createdByTextField.setText(booking.getCreatedBy());

        for (FacilityBooking facilityBooking : booking.getFacilityBookings()) {
            facilityBookings.add(facilityBooking);
            facilityBookingTableView.setItems(facilityBookings);

        }
    }
}