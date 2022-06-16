package com.epam.billing.service;

import com.epam.billing.dto.ActivityCategoryLocActivityUserActivityCountDTO;
import com.epam.billing.dto.ActivityIdActivityCategoryLocalizedNameActivityNameDTO;
import com.epam.billing.entity.Activity;
import com.epam.billing.repository.ActivityRepository;

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

    public List<ActivityIdActivityCategoryLocalizedNameActivityNameDTO> getAllWithCategoryLocalizedNames(int languageId) {
        return activityRepository.getAllWithCategoryLocalizedNames(languageId);
    }

    public List<ActivityCategoryLocActivityUserActivityCountDTO> getActivityCategoryLocActivityUserActivityCountDTO(int languageId, String criteria) {
        return activityRepository.getActivityCategoryLocActivityUserActivityCountDTO(languageId, criteria);
    }

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public boolean delete(Activity activity) {
        return activityRepository.delete(activity);
    }

    public boolean existById(int id) {
        return activityRepository.existById(id);
    }

    public Activity getById(int id) {
        return activityRepository.getById(id);
    }

    public Activity getByNameNotSafe(String name) {
        return activityRepository.getByNameNotSafe(name);
    }

    public Optional<Activity> getByNameSafe(String name) {
        return activityRepository.getByNameSafe(name);
    }

    public Optional<Activity> getByNameInOneCategory(String name, int categoryId) {
        return activityRepository.getByNameInOneCategory(name, categoryId);
    }

    public Activity update(Activity activity) {
        return activityRepository.update(activity);
    }

}
