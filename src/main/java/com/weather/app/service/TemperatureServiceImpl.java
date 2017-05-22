package com.weather.app.service;

import com.weather.app.dao.DefualtTemperatureRepository;
import com.weather.app.dao.TemperatureRepository;
import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.TemperatureNote;
import com.weather.app.util.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.NonUniqueResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Mahmoud.Fathy on 5/20/2017.
 */
@Service
public class TemperatureServiceImpl implements TemperatureService {
    private static final Logger LOGGER = Logger.getLogger(TemperatureServiceImpl.class);
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private DefualtTemperatureRepository defualtTemperatureRepository;

    @Override
    public TemperatureNote findTodaysTemperatureNote(int temp) throws ServiceException{
        LOGGER.info("findTodaysTemperatureNote method has been called");
        LOGGER.debug("Temperature is " + temp);
        try{
            TemperatureNote note = temperatureRepository.findByCreationDate(DateUtils.getStartOfToday());
            LOGGER.debug("findTodaysTemperatureNote returning note " );
            return note;
        }catch (NonUniqueResultException |IncorrectResultSizeDataAccessException exception){
            LOGGER.error("More than note have been found for that temp");
            throw new ServiceException("More than note have been found for that temp");
        }catch (Exception exception) {
            LOGGER.error("General error ",exception);
            throw new ServiceException("General Error");
        }
    }

    @Override
    public void addTemperatureNote(Date creationDate, String note, Long userId) throws ServiceException{
        LOGGER.info("addTemperatureNote method has been called");
        LOGGER.debug("addTemperatureNote creationDate " + creationDate+" note "+note+" userId "+userId );
        try{
            TemperatureNote temperatureNote = new TemperatureNote(creationDate, note, userId);
            temperatureRepository.save(temperatureNote);
            LOGGER.debug(String.format("addTemperatureNote note=%s has been added",note));
        }catch(ConstraintViolationException exception){
            LOGGER.error(exception.getMessage());
            throw new ServiceException(exception.getMessage());
        } catch (Exception exception){
            LOGGER.error("General error "+exception.getMessage());
            throw new ServiceException("General Error");
        }

    }

    @Override
    public List<TemperatureNote> findNotesByUserId(Long userId) {
        LOGGER.info("findNotesByUserId method has been called");
        LOGGER.debug("userId is " + userId);
        List<TemperatureNote> notes = temperatureRepository.findByUserIdOrderByCreationDate(userId);
        LOGGER.debug("findNotesByUserId returning note " + notes );
        return notes;
    }

    @Override
    public List<DefaultTemperatureNote> findAllDefaultNotes() {

        return defualtTemperatureRepository.findAll();
    }

    @Override
    public DefaultTemperatureNote findDefaultNoteByFromTempBetween(int temp) throws ServiceException{
        LOGGER.info("findDefaultNoteByFromTempBetween method has been called");
        LOGGER.debug("Temperature is " + temp);
        try{
            DefaultTemperatureNote defaultTemperatureNote = defualtTemperatureRepository.findByFromTempBetween(temp);
            LOGGER.debug("findDefaultNoteByFromTempBetween returning note " + defaultTemperatureNote );
            return defaultTemperatureNote;
        }catch (NonUniqueResultException |IncorrectResultSizeDataAccessException exception){
            LOGGER.error("More than default note have been found for that temp");
            throw new ServiceException("More than default note have been found for that temp");
        }catch (Exception exception) {
            LOGGER.error("General error ",exception);
            throw new ServiceException("General Error");
        }

    }

    @Override
    public TemperatureNote findNoteByCreationDateBetween(Date fromDate, Date toDate) throws ServiceException {
        LOGGER.info("findNoteByCreationDateBetween method has been called");
        LOGGER.debug("fromDate is " + fromDate+" toDate is "+toDate);
        try{
            TemperatureNote note = temperatureRepository.findByCreationDate(fromDate);
            LOGGER.debug("findDefaultNoteByFromTempBetween returning note " + note );
            return note;
        }catch (NonUniqueResultException |IncorrectResultSizeDataAccessException exception){
            LOGGER.error(String.format("More than note have been found between for Date=%s", fromDate));
            throw new ServiceException(String.format("More than note have been found between for Date=%s", fromDate));
        }catch (Exception exception) {
            LOGGER.error("General error ",exception);
            throw new ServiceException("General Error");
        }

    }

    @Override
    public void saveTemperatatureNote(TemperatureNote note) {
        LOGGER.info("saveTemperatatureNote method has been called");
        LOGGER.debug("note " + note);
        try{
            temperatureRepository.save(note);
        } catch(ConstraintViolationException exception){
            LOGGER.error(exception.getMessage());
            throw new ServiceException(exception.getMessage());
        } catch (Exception exception){
            LOGGER.error("General error ",exception);
            throw new ServiceException("General Error");
        }

        LOGGER.debug("saveTemperatatureNote exit");
    }


}
