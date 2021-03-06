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

public class FXMLFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public Stage build(URL fxmlLocation) {
		FXMLLoader loader = new FXMLLoader();

		loader.setControllerFactory(new Callback<Class<?>, Object>() {
			@Override
			public Object call(Class<?> type) {
				return applicationContext.getBean(type);
			}
		});

		try {
			loader.setLocation(fxmlLocation);
			Parent root = loader.load();
			Scene scene = new Scene(root);
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