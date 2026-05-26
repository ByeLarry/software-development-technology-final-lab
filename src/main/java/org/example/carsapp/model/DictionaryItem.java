package org.example.carsapp.model;

public class DictionaryItem {

	private final int id;
	private final String name;

	public DictionaryItem(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// Нужно, чтобы ComboBox показывал название, а не адрес объекта
	@Override
	public String toString() {
		return name;
	}
}