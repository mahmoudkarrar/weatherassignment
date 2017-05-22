package com.weather.app.service;

import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.TemperatureNote;

import java.util.Date;
import java.util.List;

/**
 * Created by Mahmoud.Fathy on 5/20/2017.
 */
public interface TemperatureService {
    public TemperatureNote findTodaysTemperatureNote(int temp);
    public void addTemperatureNote(Date creationDate, String note, Long userId);
    public List<TemperatureNote> findNotesByUserId(Long userId);
    public List<DefaultTemperatureNote> findAllDefaultNotes();
    public DefaultTemperatureNote findDefaultNoteByFromTempBetween(int temp);
    public TemperatureNote findNoteByCreationDateBetween(Date fromDate, Date toDate);
    public void saveTemperatatureNote(TemperatureNote note);
}
