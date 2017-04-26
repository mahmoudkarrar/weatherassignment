package com.weather.app.com.weather.app.dao;

import com.weather.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>User Repository!</h1>
 * handles all data manipulation for User
 * @author  Mahmoud Fathy
 * @version 1.0
 * @since   2017-04-24
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    /**
     * This method is used to retrieve user by email.
     * @param email user email.
     * @return List<TemperatureNote>.
     */
    public User findByEmailEquals(String email);
}
