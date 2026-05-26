package org.example.carsapp.service;

import org.example.carsapp.model.Car;
import org.example.carsapp.repository.CarRepository;

import java.util.List;

public class CarService {

	private final CarRepository carRepository;

	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public List<Car> getAllCars() {
		return carRepository.findAll();
	}

	public void addCar(
			int modelId,
			int bodyTypeId,
			int fuelTypeId,
			int transmissionId,
			String color,
			int productionYear,
			double price,
			int mileage,
			boolean available
	) {
		validateCarData(color, productionYear, price, mileage);

		carRepository.addCar(
				modelId,
				bodyTypeId,
				fuelTypeId,
				transmissionId,
				color,
				productionYear,
				price,
				mileage,
				available
		);
	}

	public void deleteCar(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Некорректный идентификатор автомобиля");
		}

		carRepository.deleteById(id);
	}

	private void validateCarData(
			String color,
			int productionYear,
			double price,
			int mileage
	) {
		if (color == null || color.isBlank()) {
			throw new IllegalArgumentException("Цвет автомобиля не должен быть пустым");
		}

		if (productionYear < 1900 || productionYear > 2100) {
			throw new IllegalArgumentException("Год выпуска должен быть в диапазоне от 1900 до 2100");
		}

		if (price <= 0) {
			throw new IllegalArgumentException("Цена автомобиля должна быть больше 0");
		}

		if (mileage < 0) {
			throw new IllegalArgumentException("Пробег автомобиля не может быть отрицательным");
		}
	}
}