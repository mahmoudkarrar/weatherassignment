package com.weather.app;

import com.weather.app.com.weather.app.dao.DefualtTemperatureRepository;
import com.weather.app.com.weather.app.dao.TemperatureRepository;
import com.weather.app.com.weather.app.util.DateUtils;
import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.TemperatureNote;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <h1>Temperature Notes Controller!</h1>
 * RESTful controller class exposes adding, retrieving notes.
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-25
 */
@RestController
@RequestMapping(value = "/temperature")
public class TemperatureNotesController {

    private static final Logger LOGGER = Logger.getLogger(TemperatureNotesController.class);
    private TemperatureRepository temperatureRepository;
    private DefualtTemperatureRepository defualtTemperatureRepository;

    @Autowired
    public TemperatureNotesController(TemperatureRepository temperatureRepository, DefualtTemperatureRepository defualtTemperatureRepository) {
        this.temperatureRepository = temperatureRepository;
        this.defualtTemperatureRepository = defualtTemperatureRepository;
    }
    /**
     * This method is used to retrieve note if not added
     * by admin will get default note.
     * @param temp int.
     * @return String note.
     */
    @RequestMapping(value = "/note/{temp}")
    public String getNote(@PathVariable int temp) {
        LOGGER.info("getNote method has been called");
        LOGGER.debug("Temperature is " + temp);
        TemperatureNote note = temperatureRepository.findByCreationDateBetween(DateUtils.getStartOfToday(),
                DateUtils.getEndOfToday());

        if (note != null) {
            LOGGER.debug("getNote returning note " + note.getNote());
            return note.getNote();
        }
        LOGGER.info("getNote search for default note");
        DefaultTemperatureNote defaultTemperatureNote = defualtTemperatureRepository.findByFromTempBetween(temp);

        return defaultTemperatureNote == null ? null : defaultTemperatureNote.getNote();
    }
    /**
     * This method is used to create new weather note
     * by admin.
     * @param note to be added.
     * @return List<TemperatureNote> all user available notes.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public List<TemperatureNote> addNote(@RequestBody TemperatureNote note) {
        LOGGER.info("addNote method has been called");
        LOGGER.debug("Note to be added is " + note);
        Date fromDate = DateUtils.getStartOfToday();
        Date toDate = DateUtils.getEndOfToday();
        TemperatureNote oldNote = temperatureRepository.findByCreationDateBetween(fromDate, toDate);
        if (oldNote != null) {
            LOGGER.debug("Update old note");
            oldNote.setNote(note.getNote());
            temperatureRepository.save(oldNote);
            return temperatureRepository.findByUserIdOrderByCreationDate(note.getUserId());
        }
        note.setCreationDate(Calendar.getInstance().getTime());
        temperatureRepository.save(note);
        LOGGER.debug("Today's note has been added successfully!");
        return temperatureRepository.findByUserIdOrderByCreationDate(note.getUserId());
    }
    /**
     * This method is used to retrieve notes for user.
     * @param userId Long.
     * @return List<TemperatureNote> all user available notes.
     */
    @RequestMapping(value = "/allnotes/{userId}", method = RequestMethod.GET)
    public List<TemperatureNote> findNotesByUserId(@PathVariable Long userId) {
        LOGGER.info("findNotesByUserId method has been called");
        LOGGER.debug("UserId is " + userId);
        List<TemperatureNote> tempNotes = temperatureRepository.findByUserIdOrderByCreationDate(userId);
        String message = tempNotes == null || tempNotes.size() == 0 ? "0" :
                "" + tempNotes.size();
        LOGGER.debug(message + " have been found for user id " + userId);
        return tempNotes;
    }
    /**
     * This method is used to retrieve notes for user.
     * @return List<DefaultTemperatureNote> all default notes.
     */
    @RequestMapping(value = "/defaultnotes", method = RequestMethod.GET)
    public List<DefaultTemperatureNote> findDefaultNotes() {
        LOGGER.info("findNotesByUserId method has been called");
        return defualtTemperatureRepository.findAll();
    }

}
