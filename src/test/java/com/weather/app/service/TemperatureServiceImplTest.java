package com.weather.app.service;

import com.weather.app.dao.DefualtTemperatureRepository;
import com.weather.app.dao.TemperatureRepository;
import com.weather.app.dao.UsersRepository;
import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.TemperatureNote;
import com.weather.app.model.User;
import com.weather.app.util.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mahmoud.Fathy on 5/21/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TemperatureServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DefualtTemperatureRepository defualtTemperatureRepository;

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TemperatureServiceImpl temperatureService;


    private User user;

    @Before
    public void setup(){
        this.temperatureRepository.deleteAllInBatch();
        this.usersRepository.deleteAllInBatch();

        entityManager.persist(new User("Mahmoud", "mahmoud@gmail.com", "Y", "1234", "0128188838"));
        user = usersRepository.findByEmailEquals("mahmoud@gmail.com");
    }

    @Test
    public void findTodaysTemperatureNote() throws Exception {
        TemperatureNote note = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note);
        TemperatureNote todayNote = temperatureService.findTodaysTemperatureNote(5);
        assertNotNull(todayNote);
        assertEquals(note.getNote(), todayNote.getNote());
        assertEquals(note.getId(), todayNote.getId());

    }

    @Test(expected = ServiceException.class)
    public void findTodaysTemperatureNoteFails() throws Exception {
        TemperatureNote note = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note);
        TemperatureNote note2 = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note2);
        TemperatureNote todayNote = temperatureService.findTodaysTemperatureNote(5);

    }

    @Test
    public void addTemperatureNote() throws Exception {
        temperatureService.addTemperatureNote(new Date(), "Chilly weather", user.getId());
        List<TemperatureNote> note = this.temperatureRepository.findByUserIdOrderByCreationDate(user.getId());
        assertNotNull(note);
        assertEquals(1, note.size());
        assertEquals("Chilly weather", note.get(0).getNote() );

    }

    @Test
    public void findNotesByUserId() throws Exception {
        temperatureService.addTemperatureNote(new Date(), "Chilly weather", user.getId());
        List<TemperatureNote> note = this.temperatureService.findNotesByUserId(user.getId());
        assertNotNull(note);
        assertEquals(1, note.size());
        assertEquals(user.getId(), note.get(0).getUserId().longValue());
        assertEquals("Chilly weather", note.get(0).getNote() );
    }

    @Test
    public void findAllDefaultNotes() throws Exception {
        List<DefaultTemperatureNote> defaultTemperatureNotes = this.temperatureService.findAllDefaultNotes();
        assertNotNull(defaultTemperatureNotes);
        defaultTemperatureNotes.stream().forEach(j -> assertNotNull(j.getNote()));
        defaultTemperatureNotes.stream().filter(j -> j.getFromTemp() == 1 &&
        j.getToTemp() == 10).forEach(j -> assertEquals("Chilly weather", j.getNote()));
    }

    @Test
    public void findDefaultNoteByFromTempBetween() throws Exception {
        DefaultTemperatureNote defaultTemperatureNotes = this.temperatureService.findDefaultNoteByFromTempBetween(5);
        assertNotNull(defaultTemperatureNotes);
       assertEquals("Chilly weather", defaultTemperatureNotes.getNote());
    }

    @Test
    public void findNoteByCreationDateBetween() throws Exception {
        TemperatureNote note = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note);
        TemperatureNote todayNote = temperatureService.findNoteByCreationDateBetween(DateUtils.getStartOfToday(), DateUtils.getEndOfToday());
        assertNotNull(todayNote);
        assertEquals(note.getNote(), todayNote.getNote());
        assertEquals(note.getId(), todayNote.getId());
    }

    @Test(expected = ServiceException.class)
    public void findNoteByCreationDateBetweenFails() throws Exception {
        TemperatureNote note = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note);
        TemperatureNote note2 = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        this.temperatureRepository.save(note2);
        temperatureService.findNoteByCreationDateBetween(DateUtils.getStartOfToday(), DateUtils.getEndOfToday());
    }

    @Test
    public void saveTemperatatureNote() throws Exception {
        TemperatureNote note = new TemperatureNote(new Date(), "Chilly weather", user.getId());
        temperatureService.saveTemperatatureNote(note);

       assertNotEquals(0,note);
    }

}