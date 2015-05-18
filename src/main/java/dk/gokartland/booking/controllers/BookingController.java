package dk.gokartland.booking.controllers;

import dk.gokartland.booking.controllers.helpers.CalendarFormatHelper;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.domain.exceptions.MissingInformationException;
import dk.gokartland.booking.domain.exceptions.NoFacilityBookingsException;
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
    TextField nameTextField, phoneTextField, emailTextField, createdByTextField, referenceNumberTextField;

    @FXML
    Button addGokartButton, addPaintballButton, addLasertagButton, addDiningButton, createButton;

    @FXML
    RadioButton privateRadioButton;

    @FXML
    CheckBox needsPermissionCheckBox, isPaidCheckBox;

    @FXML
    TextArea commentsTextArea;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn, deleteColumn;

    @FXML
    Label titleLabel, errorLabel;

    @FXML
    GridPane mainGrid, bottomGrid;

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

                Booking booking = null;
                try {

                    booking = bookingService.createBooking(nameTextField.getText(), phoneTextField.getText(), isPrivateClient, needsPermissionCheckBox.isSelected(), emailTextField.getText(), commentsTextArea.getText(), createdByTextField.getText(), isPaidCheckBox.isSelected(), referenceNumberTextField.getText(), facilityBookings);

                    setChanged();
                    notifyObservers(booking);
                    clearChanged();

                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.close();

                } catch (NoFacilityBookingsException e) {
                    errorLabel.setText("Tilføj venligst mindst en aktivitet");
                } catch (MissingInformationException e) {
                    errorLabel.setText("Udfyld venligste alle felter market med *");
                }

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
                return new SimpleStringProperty( CalendarFormatHelper.toFormattedString(facilityBooking.getValue().getFrom()) );
            }
        });


        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                return new SimpleStringProperty(CalendarFormatHelper.toFormattedString(facilityBooking.getValue().getTo()));
            }
        });

        deleteColumn.setCellFactory(new Callback<TableColumn<FacilityBooking, String> , TableCell<FacilityBooking, String>>() {

            @Override
            public TableCell<FacilityBooking, String> call(TableColumn<FacilityBooking, String> p) {
                return new TableCell<FacilityBooking, String>() {

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            Button button = new Button("Slet");
                            setGraphic(button);

                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    FacilityBooking facilityBooking = facilityBookingTableView.getSelectionModel().getSelectedItem();
                                    //bookingService.deleteFacilityBooking(facilityBooking);
                                    // Make delete function work
                                }
                            });
                        }
                    }
                };
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
                    else if (facilityBooking instanceof PaintballBooking)
                        loader = fxmlFactory.build(getClass().getResource("paintball.fxml"));
                    else if (facilityBooking instanceof LasertagBooking)
                        loader = fxmlFactory.build(getClass().getResource("lasertag.fxml"));
                    else
                        loader = fxmlFactory.build(getClass().getResource("dining.fxml"));

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
                try {
                    booking.changeCustomerName(nameTextField.getText());
                    booking.changePhoneNumber(phoneTextField.getText());
                    booking.changeEmail(emailTextField.getText());
                    booking.changeComments(commentsTextArea.getText());
                    booking.changeNeedsPermission(needsPermissionCheckBox.isSelected());
                    booking.changePrivateClient(privateRadioButton.isSelected());
                    booking.changeCreatedBy(createdByTextField.getText());
                    booking.changePaid(isPaidCheckBox.isSelected());
                    booking.changeReferenceNumber(referenceNumberTextField.getText());
                } catch (MissingInformationException e) {
                    errorLabel.setText("Udfyld venligst alle felter markeret med *");
                }

                // Insert bookingService updateMethod and use facilityBooking below
                bookingService.updateBooking(booking);

                // Observer pattern notify main window
                setChanged();
                notifyObservers(booking);
                clearChanged();

                // Close window
                Stage stage = (Stage) root.getScene().getWindow();
                stage.close();

                for (FacilityBooking facilityBooking : facilityBookings) {
                    booking.addFacilityBooking(facilityBooking);
                }

            }
        });

        Button deleteButton = new Button("Slet");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Insert delete Booking logic
            }
        });

        bottomGrid.getChildren().remove(createButton);
        bottomGrid.add(updateButton, 1, 11);
        bottomGrid.add(deleteButton, 2, 11);

        nameTextField.setText(booking.getCustomerName());
        phoneTextField.setText(booking.getPhoneNumber());
        emailTextField.setText(booking.getEmail());
        privateRadioButton.selectedProperty().setValue(booking.isPrivateClient());
        needsPermissionCheckBox.selectedProperty().setValue(booking.isNeedsPermission());
        commentsTextArea.setText(booking.getComments());
        createdByTextField.setText(booking.getCreatedBy());
        isPaidCheckBox.selectedProperty().setValue(booking.getIsPaid());
        referenceNumberTextField.setText(booking.getReferenceNumber());

        for (FacilityBooking facilityBooking : booking.getFacilityBookings()) {
            facilityBookings.add(facilityBooking);
            facilityBookingTableView.setItems(facilityBookings);

        }
    }
}