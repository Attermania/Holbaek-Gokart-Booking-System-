package dk.gokartland.booking.factories;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class FXMLFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public Stage build(URL fxmlLocation) {
		return build(fxmlLocation, null);
	}

	public Stage build(URL fxmlLocation, Observer observer) {
		FXMLLoader loader = new FXMLLoader();

		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				Object controller = applicationContext.getBean(type);

				if(controller instanceof Observable && observer != null) {
					Observable observable = (Observable) controller;
					observable.addObserver(observer);
				}

				return controller;
			}
		});

		try {
			loader.setLocation(fxmlLocation);

			// The root element from the fxml file
			Parent root = loader.load();

			// Scene - The container of the root element
			Scene scene = new Scene(root);

			// Stage - a window
			Stage stage = new Stage();
			stage.setScene(scene);

			return stage;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}