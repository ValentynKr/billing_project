package com.epam.billing.service;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;
import com.epam.billing.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    public boolean existById(long id) {
        return userRepository.existById(id);
    }

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public Optional<User> getByEmail(String email) throws DBException {
        return userRepository.getByEmail(email);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

}
