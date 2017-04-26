package com.weather.app.com.weather.app.dao;

import com.weather.app.com.weather.app.util.DateUtils;
import com.weather.app.model.TemperatureNote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mahmoud.Fathy on 4/24/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TemperatureRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TemperatureRepository temperatureRepository;

    private Date now = DateUtils.now();

    @Before
    public void setup(){
        temperatureRepository.deleteAllInBatch();

        this.entityManager.persist(new TemperatureNote(now, "cold", 1L));
    }


    @Test
    public void findByUserIdOrderByCreationDate() throws Exception {

        List<TemperatureNote> notes = temperatureRepository.findByUserIdOrderByCreationDate(1L);
        assertTrue(notes != null && !notes.isEmpty());

        assertTrue(notes.get(0).equals(new TemperatureNote(now, "cold", 1L)));
    }

}