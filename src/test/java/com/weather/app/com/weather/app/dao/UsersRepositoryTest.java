package com.weather.app.com.weather.app.dao;

import com.weather.app.model.User;
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
public class UsersRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void findByEmailEquals() throws Exception {
        this.entityManager.persist(new User("Mahmoud", "mahmoud@email.com", "N", "1234", "0100000000"));
        User user = usersRepository.findByEmailEquals("mahmoud@email.com");
        assertNotNull(user);
        assertEquals(user.getEmail(), "mahmoud@email.com");
    }

}