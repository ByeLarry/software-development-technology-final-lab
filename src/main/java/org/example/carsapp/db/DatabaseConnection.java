package org.example.carsapp.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final Dotenv dotenv = Dotenv.configure()
			.ignoreIfMissing()
			.load();

	private static final String URL = getEnv("DB_URL");
	private static final String USER = getEnv("DB_USER");
	private static final String PASSWORD = getEnv("DB_PASSWORD");

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	private static String getEnv(String key) {
		String value = dotenv.get(key);

		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Не задана переменная окружения: " + key);
		}

		return value;
	}
}