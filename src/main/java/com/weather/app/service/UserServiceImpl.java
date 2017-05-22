package com.weather.app.service;

import com.weather.app.dao.UsersRepository;
import com.weather.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mahmoud.Fathy on 5/21/2017.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public User findByEmailEquals(String email) {
        return usersRepository.findByEmailEquals(email);
    }

    @Override
    public void saveUser(User user) {
        usersRepository.save(user);
    }
}
