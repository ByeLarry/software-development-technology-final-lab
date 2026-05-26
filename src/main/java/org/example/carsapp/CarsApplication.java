package org.example.carsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarsApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(
				CarsApplication.class.getResource("/org/example/carsapp/main-view.fxml")
		);

		Scene scene = new Scene(loader.load(), 1065, 600);

		stage.setTitle("Учет автомобилей");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}