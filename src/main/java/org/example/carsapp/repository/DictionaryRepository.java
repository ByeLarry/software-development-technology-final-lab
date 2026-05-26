package org.example.carsapp.repository;

import org.example.carsapp.db.DatabaseConnection;
import org.example.carsapp.model.DictionaryItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryRepository {

	private final DatabaseConnection databaseConnection;

	public DictionaryRepository(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public List<DictionaryItem> findBrands() {
		return findAll("SELECT id, name FROM vyatsu.brands ORDER BY name;");
	}

	public List<DictionaryItem> findModels() {
		return findAll("SELECT id, name FROM vyatsu.car_models ORDER BY name;");
	}

	public List<DictionaryItem> findBodyTypes() {
		return findAll("SELECT id, name FROM vyatsu.body_types ORDER BY name;");
	}

	public List<DictionaryItem> findFuelTypes() {
		return findAll("SELECT id, name FROM vyatsu.fuel_types ORDER BY name;");
	}

	public List<DictionaryItem> findTransmissions() {
		return findAll("SELECT id, name FROM vyatsu.transmissions ORDER BY name;");
	}

	private List<DictionaryItem> findAll(String sql) {
		List<DictionaryItem> items = new ArrayList<>();

		try (
				Connection connection = databaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()
		) {
			while (resultSet.next()) {
				items.add(new DictionaryItem(
						resultSet.getInt("id"),
						resultSet.getString("name")
				));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ошибка при получении справочных данных", e);
		}

		return items;
	}
}