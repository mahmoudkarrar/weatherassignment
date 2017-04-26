package com.weather.app.com.weather.app.dao;

import com.weather.app.model.DefaultTemperatureNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <h1>Default Temperature Repository!</h1>
 * handles all data manipulation for DefaultTemperatureNote
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-24
 */
public interface DefualtTemperatureRepository extends JpaRepository<DefaultTemperatureNote, Long> {
    /**
     * This method is used to retrieve default weather
     * note that matches current weather.
     * @param fromTemp This is an integer that is the current weather.
     * @return DefaultTemperatureNote.
     */
    @Query("select t from DefaultTemperatureNote t where t.fromTemp < :temp and t.toTemp >= :temp")
    public DefaultTemperatureNote findByFromTempBetween(@Param("temp") Integer fromTemp);
}
