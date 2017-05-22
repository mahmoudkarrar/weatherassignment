package com.weather.app.controller;

import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.TemperatureNote;
import com.weather.app.service.TemperatureServiceImpl;
import com.weather.app.util.DateUtils;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * <h1>Temperature Notes Controller!</h1>
 * RESTful controller class exposes adding, retrieving notes.
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-25
 */
@RestController
@RequestMapping({"/temperature"})
public class TemperatureNotesController
{



        private static final Logger LOGGER = Logger.getLogger(TemperatureNotesController.class);
        @Autowired
        private TemperatureServiceImpl temperatureService;


        public TemperatureNotesController() {

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
            TemperatureNote note = temperatureService.findTodaysTemperatureNote(temp);

            if (note != null) {
                LOGGER.debug(String.format("getNote returning note=%s ", note.getNote()));
                return note.getNote();
            }
            LOGGER.info("getNote search for default note");
            DefaultTemperatureNote defaultTemperatureNote = temperatureService.findDefaultNoteByFromTempBetween(temp);
            LOGGER.debug(String.format("getNote returning note=%" , defaultTemperatureNote));
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
            LOGGER.debug(String.format("Note to be added is=%s ", note));
            Date fromDate = DateUtils.getStartOfToday();
            Date toDate = DateUtils.getEndOfToday();
            TemperatureNote oldNote = temperatureService.findNoteByCreationDateBetween(fromDate, toDate);
            if (oldNote != null) {
                LOGGER.debug("Update old note");
                oldNote.setNote(note.getNote());
                temperatureService.saveTemperatatureNote(oldNote);
                LOGGER.debug("addNote Today's note has been updated successfully!");
                return temperatureService.findNotesByUserId(note.getUserId());
            }

            temperatureService.addTemperatureNote(new Date(), note.getNote(), note.getUserId());
            LOGGER.debug("addNote Today's note has been added successfully!");
            return temperatureService.findNotesByUserId(note.getUserId());
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
            List<TemperatureNote> tempNotes = temperatureService.findNotesByUserId(userId);
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
            return temperatureService.findAllDefaultNotes();
        }

}
