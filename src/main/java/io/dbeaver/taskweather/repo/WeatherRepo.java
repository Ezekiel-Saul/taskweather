package io.dbeaver.taskweather.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import io.dbeaver.taskweather.models.WeatherHistory;

public interface WeatherRepo extends JpaRepository<WeatherHistory, Long>{

}
