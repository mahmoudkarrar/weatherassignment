package com.weather.app.com.weather.app.dao;

import com.weather.app.model.TemperatureNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * <h1>Temperature Repository!</h1>
 * handles all data manipulation for TemperatureNote
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-24
 */
public interface TemperatureRepository extends JpaRepository<TemperatureNote, Long> {
    /**
     * This method is used to retrieve default weather note
     * for a specific date.
     * @param fromDate date when note created.
     * @param toDate end date when note created.
     * @return TemperatureNote.
     */
    public TemperatureNote findByCreationDateBetween(Date fromDate, Date toDate);
    /**
     * This method is used to retrieve default weather notes
     * for a specific user.
     * @param userId long.
     * @return List<TemperatureNote>.
     */
    public List<TemperatureNote> findByUserIdOrderByCreationDate(Long userId);
}
