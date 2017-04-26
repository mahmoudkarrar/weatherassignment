package com.weather.app.com.weather.app.dao;

import com.weather.app.model.DefaultTemperatureNote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Mahmoud.Fathy on 4/25/2017.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DefualtTemperatureRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DefualtTemperatureRepository defualtTemperatureRepository;

    @Before
    public void setup(){
        this.defualtTemperatureRepository.deleteAllInBatch();
        this.entityManager.persist(new DefaultTemperatureNote(1, 10, "Chilly weather"));
        this.entityManager.persist(new DefaultTemperatureNote(10, 15, "Cold weather"));
    }
    @Test
    public void findByFromTempBetween() throws Exception {

        DefaultTemperatureNote note = defualtTemperatureRepository.findByFromTempBetween(5);
        assertNotNull(note);
        assertEquals("Chilly weather", note.getNote());
    }

}