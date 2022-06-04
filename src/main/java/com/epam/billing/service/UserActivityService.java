package com.epam.billing.service;

import com.epam.billing.entity.UserActivity;
import com.epam.billing.joins.UserNameJoin;
import com.epam.billing.repository.UserActivityRepository;

import java.util.List;

public class UserActivityService {


    private final UserActivityRepository userActivityRepository;


    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public List<UserNameJoin> getUserNameJoin(int userId) {return userActivityRepository.getNameActivityJoin(userId);}

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

    public List<UserActivity> getByUserId(long id) {return userActivityRepository.getByUserId(id); }
}
