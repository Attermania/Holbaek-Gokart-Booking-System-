package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.BookablePlace;
import dk.gokartland.booking.domain.Place;
import dk.gokartland.booking.services.BookingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sun.swing.BakedArrayList;

import java.awt.print.Book;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPaintballController implements Initializable {

    private BookingService bookingService;

    @FXML
    Button addButton;
    @FXML
    ComboBox<BookablePlace> placeComboBox;

    public AddPaintballController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initialize(URL location, ResourceBundle resources) {

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Insert logic
            }
        });

        List<Place> testList = new ArrayList();
        testList.add(new Place(1, "test bane", true));
        BookablePlace bookablePlace = new BookablePlace(1, "test bane", testList);

        ObservableList<BookablePlace> bookablePlacesObservableList = FXCollections.observableArrayList();
        bookablePlacesObservableList.add(bookablePlace);

        placeComboBox.setItems(bookablePlacesObservableList);



    }
}
