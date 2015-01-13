package org.strasa.middleware.model.custom;

public class RawLocationModel {
	private String Location, 
	Country,
	Province,
	Region,
	Altitude,
	Latitude,
	WeatherStation;

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getAltitude() {
		return Altitude;
	}

	public void setAltitude(String altitude) {
		Altitude = altitude;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getWeatherStation() {
		return WeatherStation;
	}

	public void setWeatherStation(String weatherStation) {
		WeatherStation = weatherStation;
	}
}
