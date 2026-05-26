package org.example.carsapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.carsapp.db.DatabaseConnection;
import org.example.carsapp.model.DictionaryItem;
import org.example.carsapp.repository.CarRepository;
import org.example.carsapp.repository.DictionaryRepository;
import org.example.carsapp.service.CarService;

public class AddCarController {

	@FXML
	private ComboBox<DictionaryItem> modelComboBox;

	@FXML
	private ComboBox<DictionaryItem> bodyTypeComboBox;

	@FXML
	private ComboBox<DictionaryItem> fuelTypeComboBox;

	@FXML
	private ComboBox<DictionaryItem> transmissionComboBox;

	@FXML
	private TextField colorField;

	@FXML
	private TextField yearField;

	@FXML
	private TextField priceField;

	@FXML
	private TextField mileageField;

	@FXML
	private CheckBox availableCheckBox;

	private CarService carService;
	private DictionaryRepository dictionaryRepository;

	@FXML
	public void initialize() {
		DatabaseConnection databaseConnection = new DatabaseConnection();

		CarRepository carRepository = new CarRepository(databaseConnection);
		carService = new CarService(carRepository);

		dictionaryRepository = new DictionaryRepository(databaseConnection);

		loadDictionaries();
	}

	private void loadDictionaries() {
		try {
			modelComboBox.setItems(FXCollections.observableArrayList(dictionaryRepository.findModels()));
			bodyTypeComboBox.setItems(FXCollections.observableArrayList(dictionaryRepository.findBodyTypes()));
			fuelTypeComboBox.setItems(FXCollections.observableArrayList(dictionaryRepository.findFuelTypes()));
			transmissionComboBox.setItems(FXCollections.observableArrayList(dictionaryRepository.findTransmissions()));

			selectFirstItems();
		} catch (RuntimeException e) {
			showError("Ошибка загрузки справочников", e.getMessage());
		}
	}

	private void selectFirstItems() {
		if (!modelComboBox.getItems().isEmpty()) {
			modelComboBox.getSelectionModel().selectFirst();
		}

		if (!bodyTypeComboBox.getItems().isEmpty()) {
			bodyTypeComboBox.getSelectionModel().selectFirst();
		}

		if (!fuelTypeComboBox.getItems().isEmpty()) {
			fuelTypeComboBox.getSelectionModel().selectFirst();
		}

		if (!transmissionComboBox.getItems().isEmpty()) {
			transmissionComboBox.getSelectionModel().selectFirst();
		}
	}

	@FXML
	private void onSaveClick() {
		try {
			DictionaryItem selectedModel = modelComboBox.getSelectionModel().getSelectedItem();
			DictionaryItem selectedBodyType = bodyTypeComboBox.getSelectionModel().getSelectedItem();
			DictionaryItem selectedFuelType = fuelTypeComboBox.getSelectionModel().getSelectedItem();
			DictionaryItem selectedTransmission = transmissionComboBox.getSelectionModel().getSelectedItem();

			if (
					selectedModel == null ||
							selectedBodyType == null ||
							selectedFuelType == null ||
							selectedTransmission == null
			) {
				showWarning("Заполнение формы", "Необходимо выбрать значения во всех выпадающих списках");
				return;
			}

			String color = colorField.getText();
			int year = Integer.parseInt(yearField.getText());
			double price = Double.parseDouble(priceField.getText());
			int mileage = Integer.parseInt(mileageField.getText());
			boolean available = availableCheckBox.isSelected();

			carService.addCar(
					selectedModel.getId(),
					selectedBodyType.getId(),
					selectedFuelType.getId(),
					selectedTransmission.getId(),
					color,
					year,
					price,
					mileage,
					available
			);

			closeWindow();
		} catch (NumberFormatException e) {
			showWarning("Ошибка ввода", "Год, цена и пробег должны быть числовыми значениями");
		} catch (IllegalArgumentException e) {
			showWarning("Ошибка проверки данных", e.getMessage());
		} catch (RuntimeException e) {
			showError("Ошибка сохранения", e.getMessage());
		}
	}

	@FXML
	private void onCancelClick() {
		closeWindow();
	}

	private void closeWindow() {
		Stage stage = (Stage) colorField.getScene().getWindow();
		stage.close();
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