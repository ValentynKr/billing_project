package com.epam.billing.service;

import com.epam.billing.entity.ActivityCategoryDescription;
import com.epam.billing.repository.ActivityCategoryDescriptionRepository;

import java.util.List;
import java.util.Optional;

public class ActivityCategoryDescriptionService {

    private final ActivityCategoryDescriptionRepository activityCategoryDescriptionRepository;

    public ActivityCategoryDescriptionService(ActivityCategoryDescriptionRepository activityCategoryDescriptionRepository) {
        this.activityCategoryDescriptionRepository = activityCategoryDescriptionRepository;
    }

    public List<ActivityCategoryDescription> getAll() {
        return activityCategoryDescriptionRepository.getAll();
    }

    public Optional<ActivityCategoryDescription> getByNameExceptId(int categoryId, String name) {
        return activityCategoryDescriptionRepository.getByNameExceptId(categoryId, name);
    }

    public void save(ActivityCategoryDescription activityCategoryDescription) {
        activityCategoryDescriptionRepository.save(activityCategoryDescription);
    }

    public boolean delete(ActivityCategoryDescription activityCategoryDescription) {
        return activityCategoryDescriptionRepository.delete(activityCategoryDescription);
    }

    public boolean existById(int id) {
        return activityCategoryDescriptionRepository.existById(id);
    }

    public ActivityCategoryDescription getById(int id) {
        return activityCategoryDescriptionRepository.getById(id);
    }

    public ActivityCategoryDescription getByIdLocalized(int categoryId, int languageId) {
        return activityCategoryDescriptionRepository.getByIdLocalized(categoryId,languageId);
    }

    public ActivityCategoryDescription update(ActivityCategoryDescription activityCategoryDescription) {
        return activityCategoryDescriptionRepository.update(activityCategoryDescription);
    }
}
