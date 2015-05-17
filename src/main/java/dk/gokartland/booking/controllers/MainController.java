package dk.gokartland.booking.controllers;

import dk.gokartland.booking.dao.BookingDAO;
import dk.gokartland.booking.domain.*;
import dk.gokartland.booking.factories.FXMLFactory;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainController implements Initializable, Observer {

    @FXML
    private Button newBookingButton, resetButton, todayButton;

    @FXML
    TableView<FacilityBooking> facilityBookingTableView;

    @FXML
    TableColumn<FacilityBooking, String> typeColumn, placeColumn, fromColumn, toColumn, numberOfPeopleColumn, customerNameColumn;

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
                FXMLLoader loader = fxmlFactory.build(getClass().getResource("booking.fxml"), self);

                try {
                    Parent root = loader.load();
                    // Scene - The container of the root element
                    Scene scene = new Scene(root);

                    // Stage - a window
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
                return new SimpleStringProperty(fromDate.get(Calendar.DAY_OF_MONTH) + "-" + (fromDate.get(Calendar.MONTH)+1) + "-" + fromDate.get(Calendar.YEAR) + " " + fromDate.get(Calendar.HOUR_OF_DAY) + ":" + fromDate.get(Calendar.MINUTE));
            }
        });

        toColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> facilityBooking) {
                Calendar toDate = facilityBooking.getValue().getTo();
                return new SimpleStringProperty(toDate.get(Calendar.DAY_OF_MONTH) + "-" + (toDate.get(Calendar.MONTH) +1) + "-" + toDate.get(Calendar.YEAR) + " " + toDate.get(Calendar.HOUR_OF_DAY) + ":" + toDate.get(Calendar.MINUTE));
            }
        });

        numberOfPeopleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> param) {
                return new SimpleObjectProperty(param.getValue().getNumberOfPeople());
            }
        });

        customerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FacilityBooking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FacilityBooking, String> param) {
                return new SimpleStringProperty(param.getValue().getBooking().getCustomerName());
            }
        });

        // Opens specific booking window
        facilityBookingTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    FXMLLoader loader = fxmlFactory.build(getClass().getResource("booking.fxml"), self);

                    try {
                        Parent root = loader.load();
                        BookingController controller = loader.getController();
                        

                        Scene scene = new Scene(root);

                        Stage stage = new Stage();
                        stage.setScene(scene);

                        controller.setupForEdit(facilityBookingTableView.getSelectionModel().getSelectedItem().getBooking());

                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
                typeSearchComboBox.getSelectionModel().select(typeAll);

            }
        });

        todayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fromDatePicker.setValue(LocalDate.now());
                toDatePicker.setValue(LocalDate.now());

                search();
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

        search();
    }

    private void search() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        Calendar from = new GregorianCalendar(fromDate.getYear(), fromDate.getMonthValue() - 1, fromDate.getDayOfMonth(), 0, 0, 0);
        Calendar to = new GregorianCalendar(toDate.getYear(), toDate.getMonthValue() - 1, toDate.getDayOfMonth(), 23, 59, 59);

        List<FacilityBooking> temporaryList = bookingDAO.getFacilityBookingsWithin(from, to);

        ObservableList<FacilityBooking> facilityBookings = FXCollections.observableArrayList();

        switch (typeSearchComboBox.getSelectionModel().getSelectedItem()) {
            case "Gokart":
                for (FacilityBooking facilityBooking : temporaryList) {
                    if (facilityBooking instanceof GokartBooking) {
                        facilityBookings.add(facilityBooking);
                    }
                }
                break;
            case "Paintball":
                for (FacilityBooking facilityBooking : temporaryList) {
                    if (facilityBooking instanceof PaintballBooking) {
                        facilityBookings.add(facilityBooking);
                    }
                }
                break;
            case "Lasertag":
                for (FacilityBooking facilityBooking : temporaryList) {
                    if (facilityBooking instanceof LasertagBooking) {
                        facilityBookings.add(facilityBooking);
                    }
                }
                break;
            case "Restaurant":
                for (FacilityBooking facilityBooking : temporaryList) {
                    if (facilityBooking instanceof RestaurantBooking) {
                        facilityBookings.add(facilityBooking);
                    }
                }
                break;
            default:
                for (FacilityBooking facilityBooking : temporaryList) {
                    facilityBookings.add(facilityBooking);
                }
        }

        facilityBookingTableView.setItems(facilityBookings);
    }

    @Override
    public void update(Observable o, Object obj) {
        search();
    }




}
