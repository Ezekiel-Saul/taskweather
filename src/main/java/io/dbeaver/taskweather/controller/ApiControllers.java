package io.dbeaver.taskweather.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dbeaver.taskweather.models.WeatherHistory;
import io.dbeaver.taskweather.repo.WeatherRepo;

@RestController
public class ApiControllers {
	
	@Autowired
	private WeatherRepo weatherRepo;	

	@GetMapping(value="/weather")
	public String getWeatherHistories(){
		
		// Find, save, return current weather
		List<WeatherHistory> weatherHistories = weatherRepo.findAll();
		LocalDate actualDate = LocalDate.now();
		WeatherHistory currentWeatherHistory = hasCurrentDate(weatherHistories, actualDate);
		if(currentWeatherHistory == null) {
			WeatherHistory wh = new WeatherHistory();
			wh.setWeatherDate(actualDate);
			wh.setWeatherValue(getYandexWeather());
			weatherRepo.save(wh);	
			return wh.getWeatherValue();
		}else {
			
			return currentWeatherHistory.getWeatherValue();
		}
		
	}
	// Search and get WeatherHistory object from List at particular date
	private static WeatherHistory hasCurrentDate(List<WeatherHistory> lwh, LocalDate date) {
		
		for(WeatherHistory wh: lwh)	{
			if(wh.getWeatherDate() == date) {
				return wh;
			}
		}
		return null;
	}
	
	private static String downloadYandexHTML() {
		
		 String urlToRead = "https://yandex.ru";
	        URL url; // The URL to read
	        HttpURLConnection conn; // The actual connection to the web page
	        BufferedReader rd; 
	        String line; 
	        String result = ""; 
	        try {
	            url = new URL(urlToRead);
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            while ((line = rd.readLine()) != null) {
	                result += line;
	            }
	            rd.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	        return result;
		
	}
	
	// get temperature value from html 
	private static String getYandexWeather() {
		String result = downloadYandexHTML();
		Pattern p = Pattern.compile("<div class='weather__temp'>(.+?)</div>", Pattern.DOTALL);
	
       Matcher m = p.matcher(result);
       m.find();
       return m.group(1);
	}
		
}
