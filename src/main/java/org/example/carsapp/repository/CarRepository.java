package org.example.carsapp.repository;

import org.example.carsapp.db.DatabaseConnection;
import org.example.carsapp.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

	private final DatabaseConnection databaseConnection;

	public CarRepository(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public List<Car> findAll() {
		String sql = """
                SELECT
                    c.id,
                    b.name AS brand_name,
                    cm.name AS model_name,
                    bt.name AS body_type_name,
                    ft.name AS fuel_type_name,
                    t.name AS transmission_name,
                    c.color,
                    c.production_year,
                    c.price,
                    c.mileage,
                    c.is_available
                FROM vyatsu.cars c
                JOIN vyatsu.car_models cm ON c.model_id = cm.id
                JOIN vyatsu.brands b ON cm.brand_id = b.id
                JOIN vyatsu.body_types bt ON c.body_type_id = bt.id
                JOIN vyatsu.fuel_types ft ON c.fuel_type_id = ft.id
                JOIN vyatsu.transmissions t ON c.transmission_id = t.id
                ORDER BY c.id;
                """;

		List<Car> cars = new ArrayList<>();

		try (
				Connection connection = databaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()
		) {
			while (resultSet.next()) {
				cars.add(mapToCar(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при получении списка автомобилей", e);
		}

		return cars;
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
		String sql = """
                INSERT INTO vyatsu.cars (
                    model_id,
                    body_type_id,
                    fuel_type_id,
                    transmission_id,
                    color,
                    production_year,
                    price,
                    mileage,
                    is_available
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

		try (
				Connection connection = databaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, modelId);
			statement.setInt(2, bodyTypeId);
			statement.setInt(3, fuelTypeId);
			statement.setInt(4, transmissionId);
			statement.setString(5, color);
			statement.setInt(6, productionYear);
			statement.setDouble(7, price);
			statement.setInt(8, mileage);
			statement.setBoolean(9, available);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при добавлении автомобиля", e);
		}
	}

	public void deleteById(int id) {
		String sql = """
                DELETE FROM vyatsu.cars
                WHERE id = ?;
                """;

		try (
				Connection connection = databaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при удалении автомобиля", e);
		}
	}

	private Car mapToCar(ResultSet resultSet) throws SQLException {
		return new Car(
				resultSet.getInt("id"),
				resultSet.getString("brand_name"),
				resultSet.getString("model_name"),
				resultSet.getString("body_type_name"),
				resultSet.getString("fuel_type_name"),
				resultSet.getString("transmission_name"),
				resultSet.getString("color"),
				resultSet.getInt("production_year"),
				resultSet.getDouble("price"),
				resultSet.getInt("mileage"),
				resultSet.getBoolean("is_available")
		);
	}
}