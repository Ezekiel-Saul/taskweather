package io.dbeaver.taskweather.models;

import javax.persistence.*;

import java.time.LocalDate;


@Entity
public class WeatherHistory {
	
	@Id
	private LocalDate weatherDate;
	
	@Column
	private String weatherValue;
	
	
	public LocalDate getWeatherDate() {
		return weatherDate;
	}
	public void setWeatherDate(LocalDate weatherDate) {
		this.weatherDate = weatherDate;
	}
	public String getWeatherValue() {
		return weatherValue;
	}
	public void setWeatherValue(String weatherValue) {
		this.weatherValue = weatherValue;
	}
	
	
	
}
