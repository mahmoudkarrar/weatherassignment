package com.weather.app.service;

import com.weather.app.model.User;

/**
 * Created by Mahmoud.Fathy on 5/21/2017.
 */
public interface UserService {
    public User findByEmailEquals(String email);
    public void saveUser(User user);
}
