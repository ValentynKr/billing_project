package com.epam.billing.service;

import com.epam.billing.entity.User;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.exeption.DBException;
import com.epam.billing.repository.UserActivityRepository;
import com.epam.billing.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserActivityService {

    private final UserActivityRepository userActivityRepository;

    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public List<UserActivity> getAll() {
        return userActivityRepository.getAll();
    }

    public void save(UserActivity userActivity) {
        userActivityRepository.save(userActivity);
    }

    public boolean delete(UserActivity userActivity) {
        return userActivityRepository.delete(userActivity);
    }

    public boolean existById(long id) {
        return userActivityRepository.existById(id);
    }

    public UserActivity getById(long id) {
        return userActivityRepository.getById(id);
    }

    public UserActivity update(UserActivity userActivity) {
        return userActivityRepository.update(userActivity);
    }
}
