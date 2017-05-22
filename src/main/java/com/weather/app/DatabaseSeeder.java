package com.weather.app;



import com.weather.app.dao.DefualtTemperatureRepository;
import com.weather.app.dao.UsersRepository;
import com.weather.app.model.DefaultTemperatureNote;
import com.weather.app.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Database Seeder!</h1>
 * implements CommandLineRunner which runs once app start
 * this class used to add test data not mandatory to be included and
 * can be deleted.
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-24
 */
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(DatabaseSeeder.class);
    private UsersRepository usersRepository;
    private DefualtTemperatureRepository temperatureRepository;

    @Autowired
    public DatabaseSeeder(UsersRepository repository, DefualtTemperatureRepository defualtTemperatureRepository) {

        usersRepository = repository;
        temperatureRepository = defualtTemperatureRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("run method called!");
        List<User> users = new ArrayList<>();
        users.add(new User("Mahmoud", "mahmoud@gmail.com", "Y", "1234", "0128188838"));
        users.add(new User("Alex", "Alex@gmail.com", "Y", "1234", "0128188838"));
        users.add(new User("mario", "mario@gmail.com", "Y", "1234", "0128188838"));
        List<User> databaseUsers = usersRepository.findAll();
        if (databaseUsers == null || databaseUsers.isEmpty())
            usersRepository.save(users);

        List<DefaultTemperatureNote> notes = new ArrayList<>();
        notes.add(new DefaultTemperatureNote(1, 10, "Chilly weather"));
        notes.add(new DefaultTemperatureNote(10, 15, "Cold weather"));
        notes.add(new DefaultTemperatureNote(15, 20, "Warm weather"));
        notes.add(new DefaultTemperatureNote(20, 100, "Hot weather"));

        List<DefaultTemperatureNote> savedNotes = temperatureRepository.findAll();
        if (savedNotes == null || savedNotes.isEmpty())
            temperatureRepository.save(notes);
    }
}
