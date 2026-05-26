package org.example.carsapp;

import org.example.carsapp.model.Car;
import org.example.carsapp.repository.CarRepository;
import org.example.carsapp.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	private CarService carService;

	@BeforeEach
	void setUp() {
		carService = new CarService(carRepository);
	}

	@Test
	void getAllCarsShouldReturnCarsFromRepository() {
		List<Car> expectedCars = List.of(
				new Car(
						1,
						"Toyota",
						"Corolla",
						"sedan",
						"petrol",
						"manual",
						"white",
						2019,
						18000.00,
						60000,
						true
				)
		);

		when(carRepository.findAll()).thenReturn(expectedCars);

		List<Car> actualCars = carService.getAllCars();

		assertEquals(expectedCars, actualCars);
		verify(carRepository, times(1)).findAll();
	}

	@Test
	void addCarShouldCallRepositoryWhenDataIsValid() {
		int modelId = 1;
		int bodyTypeId = 1;
		int fuelTypeId = 1;
		int transmissionId = 1;
		String color = "white";
		int productionYear = 2020;
		double price = 25000.00;
		int mileage = 50000;
		boolean available = true;

		carService.addCar(
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

		verify(carRepository, times(1)).addCar(
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

	@Test
	void addCarShouldThrowExceptionWhenColorIsEmpty() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> carService.addCar(
						1,
						1,
						1,
						1,
						"",
						2020,
						25000.00,
						50000,
						true
				)
		);

		assertEquals("Цвет автомобиля не должен быть пустым", exception.getMessage());
		verify(carRepository, never()).addCar(
				anyInt(),
				anyInt(),
				anyInt(),
				anyInt(),
				anyString(),
				anyInt(),
				anyDouble(),
				anyInt(),
				anyBoolean()
		);
	}

	@Test
	void addCarShouldThrowExceptionWhenYearIsInvalid() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> carService.addCar(
						1,
						1,
						1,
						1,
						"black",
						1800,
						25000.00,
						50000,
						true
				)
		);

		assertEquals("Год выпуска должен быть в диапазоне от 1900 до 2100", exception.getMessage());
		verify(carRepository, never()).addCar(
				anyInt(),
				anyInt(),
				anyInt(),
				anyInt(),
				anyString(),
				anyInt(),
				anyDouble(),
				anyInt(),
				anyBoolean()
		);
	}

	@Test
	void addCarShouldThrowExceptionWhenPriceIsInvalid() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> carService.addCar(
						1,
						1,
						1,
						1,
						"black",
						2020,
						0,
						50000,
						true
				)
		);

		assertEquals("Цена автомобиля должна быть больше 0", exception.getMessage());
		verify(carRepository, never()).addCar(
				anyInt(),
				anyInt(),
				anyInt(),
				anyInt(),
				anyString(),
				anyInt(),
				anyDouble(),
				anyInt(),
				anyBoolean()
		);
	}

	@Test
	void addCarShouldThrowExceptionWhenMileageIsInvalid() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> carService.addCar(
						1,
						1,
						1,
						1,
						"black",
						2020,
						25000.00,
						-1,
						true
				)
		);

		assertEquals("Пробег автомобиля не может быть отрицательным", exception.getMessage());
		verify(carRepository, never()).addCar(
				anyInt(),
				anyInt(),
				anyInt(),
				anyInt(),
				anyString(),
				anyInt(),
				anyDouble(),
				anyInt(),
				anyBoolean()
		);
	}

	@Test
	void deleteCarShouldCallRepositoryWhenIdIsValid() {
		carService.deleteCar(1);

		verify(carRepository, times(1)).deleteById(1);
	}

	@Test
	void deleteCarShouldThrowExceptionWhenIdIsInvalid() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> carService.deleteCar(0)
		);

		assertEquals("Некорректный идентификатор автомобиля", exception.getMessage());
		verify(carRepository, never()).deleteById(anyInt());
	}
}