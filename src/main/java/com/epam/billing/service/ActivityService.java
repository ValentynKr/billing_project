package com.epam.billing.service;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;
import com.epam.billing.repository.ActivityRepository;
import com.epam.billing.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAll() {
        return activityRepository.getAll();
    }

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public boolean delete(Activity activity) {
        return activityRepository.delete(activity);
    }

    public boolean existById(long id) {
        return activityRepository.existById(id);
    }

    public Activity getById(long id) { return activityRepository.getById(id); }

    public Activity getByName(String name) { return activityRepository.getByName(name); }

    public Activity update(Activity activity) {
        return activityRepository.update(activity);
    }

}
