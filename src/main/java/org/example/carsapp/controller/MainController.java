package org.example.carsapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.carsapp.CarsApplication;
import org.example.carsapp.db.DatabaseConnection;
import org.example.carsapp.model.Car;
import org.example.carsapp.repository.CarRepository;
import org.example.carsapp.service.CarService;

import java.io.IOException;

public class MainController {

	@FXML
	private TableView<Car> carsTable;

	@FXML
	private TableColumn<Car, Integer> idColumn;

	@FXML
	private TableColumn<Car, String> brandColumn;

	@FXML
	private TableColumn<Car, String> modelColumn;

	@FXML
	private TableColumn<Car, String> bodyTypeColumn;

	@FXML
	private TableColumn<Car, String> fuelTypeColumn;

	@FXML
	private TableColumn<Car, String> transmissionColumn;

	@FXML
	private TableColumn<Car, String> colorColumn;

	@FXML
	private TableColumn<Car, Integer> yearColumn;

	@FXML
	private TableColumn<Car, Double> priceColumn;

	@FXML
	private TableColumn<Car, Integer> mileageColumn;

	@FXML
	private TableColumn<Car, Boolean> availableColumn;

	private CarService carService;

	@FXML
	public void initialize() {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		CarRepository carRepository = new CarRepository(databaseConnection);
		carService = new CarService(carRepository);

		configureTable();
		loadCars();
	}

	private void configureTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		modelColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
		bodyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bodyTypeName"));
		fuelTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fuelTypeName"));
		transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmissionName"));
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("productionYear"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
	}

	private void loadCars() {
		try {
			carsTable.setItems(FXCollections.observableArrayList(carService.getAllCars()));
		} catch (RuntimeException e) {
			showError("Ошибка загрузки данных", e.getMessage());
		}
	}

	@FXML
	private void onRefreshClick() {
		loadCars();
	}

	@FXML
	private void onAddClick() {
		try {
			FXMLLoader loader = new FXMLLoader(
					CarsApplication.class.getResource("/org/example/carsapp/add-car-view.fxml")
			);

			Scene scene = new Scene(loader.load(), 450, 400);

			Stage stage = new Stage();
			stage.setTitle("Добавление автомобиля");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.showAndWait();

			loadCars();
		} catch (IOException e) {
			showError("Ошибка открытия формы", e.getMessage());
		}
	}

	@FXML
	private void onDeleteClick() {
		Car selectedCar = carsTable.getSelectionModel().getSelectedItem();

		if (selectedCar == null) {
			showWarning("Удаление автомобиля", "Выберите автомобиль для удаления");
			return;
		}

		try {
			carService.deleteCar(selectedCar.getId());
			loadCars();
		} catch (RuntimeException e) {
			showError("Ошибка удаления", e.getMessage());
		}
	}

	private void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showWarning(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}