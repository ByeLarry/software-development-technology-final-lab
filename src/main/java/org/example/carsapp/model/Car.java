package org.example.carsapp.model;

public class Car {

	private final int id;
	private final String brandName;
	private final String modelName;
	private final String bodyTypeName;
	private final String fuelTypeName;
	private final String transmissionName;
	private final String color;
	private final int productionYear;
	private final double price;
	private final int mileage;
	private final boolean available;

	public Car(
			int id,
			String brandName,
			String modelName,
			String bodyTypeName,
			String fuelTypeName,
			String transmissionName,
			String color,
			int productionYear,
			double price,
			int mileage,
			boolean available
	) {
		this.id = id;
		this.brandName = brandName;
		this.modelName = modelName;
		this.bodyTypeName = bodyTypeName;
		this.fuelTypeName = fuelTypeName;
		this.transmissionName = transmissionName;
		this.color = color;
		this.productionYear = productionYear;
		this.price = price;
		this.mileage = mileage;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public String getBodyTypeName() {
		return bodyTypeName;
	}

	public String getFuelTypeName() {
		return fuelTypeName;
	}

	public String getTransmissionName() {
		return transmissionName;
	}

	public String getColor() {
		return color;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public double getPrice() {
		return price;
	}

	public int getMileage() {
		return mileage;
	}

	public boolean isAvailable() {
		return available;
	}
}