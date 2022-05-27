package com.epam.billing.service;

import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;
import com.epam.billing.repository.ActivityCategoryRepository;
import com.epam.billing.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ActivityCategoryService {

    private final ActivityCategoryRepository activityCategoryRepository;

    public ActivityCategoryService(ActivityCategoryRepository activityCategoryRepository) {
        this.activityCategoryRepository = activityCategoryRepository;
    }

    public List<ActivityCategory> getAll() {
        return activityCategoryRepository.getAll();
    }

    public void save(ActivityCategory activityCategory) {
        activityCategoryRepository.save(activityCategory);
    }

    public boolean delete(ActivityCategory activityCategory) {
        return activityCategoryRepository.delete(activityCategory);
    }

    public boolean existById(long id) {
        return activityCategoryRepository.existById(id);
    }

    public ActivityCategory getById(long id) {
        return activityCategoryRepository.getById(id);
    }


    public ActivityCategory update(ActivityCategory activityCategory) {
        return activityCategoryRepository.update(activityCategory);
    }


}
