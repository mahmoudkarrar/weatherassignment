package com.weather.app;

import com.weather.app.com.weather.app.dao.UsersRepository;
import com.weather.app.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>User Controller!</h1>
 * RESTful controller class exposes register, retrieving users.
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-25
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private UsersRepository usersRepository;

    @Autowired
    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    /**
     * This method is used to retrieve user by email.
     * @param user.
     * @return user.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User findUser(@RequestBody User user) {
        LOGGER.info("findUser method has been called");
        LOGGER.debug("User email is " + user.getEmail());
        User sysUser = usersRepository.findByEmailEquals(user.getEmail());
        if (sysUser != null)
            LOGGER.debug("User exists!");
        else
            LOGGER.warn("User doesn't exist");
        return sysUser;
    }
    /**
     * This method is used to register new user.
     * @param user.
     * @return boolean.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user) {
        LOGGER.info("addUser method has been called");
        LOGGER.debug("User data " + user);
        User sysUser = usersRepository.findByEmailEquals(user.getEmail());
        if (sysUser != null) {
            LOGGER.warn("User " + user.getEmail() + " already exists");
            return false;
        }
        usersRepository.save(user);
        LOGGER.debug("User data added successfully!");
        return true;
    }

}
