package dk.gokartland.booking.controllers;

import dk.gokartland.booking.factories.FXMLFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 05-05-2015.
 */
public class BookingController implements Initializable {

    @FXML
    Button addGokartButton, addPaintballButton, addLasertagButton, addDiningButton, createButton;

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
    }
}
