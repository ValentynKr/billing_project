package com.epam.billing.service;

import com.epam.billing.DTO.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.repository.ActivityCategoryRepository;

import java.util.List;

public class ActivityCategoryService {

    private final ActivityCategoryRepository activityCategoryRepository;

    public ActivityCategoryService(ActivityCategoryRepository activityCategoryRepository) {
        this.activityCategoryRepository = activityCategoryRepository;
    }

    public List<ActivityCategory> getAll() {
        return activityCategoryRepository.getAll();
    }

    public List<ActivityCategory> getAllWithLocalizedNames(int languageId) {
        return activityCategoryRepository.getAllWithLocalizedNames(languageId);
    }

    public List<ActivityCategory> getOpenedWithLocalizedNames(int languageId) {
        return activityCategoryRepository.getOpenedWithLocalizedNames(languageId);
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getAllWithLocalizedNameStatusDTO(int languageId) {
        return activityCategoryRepository.getAllWithLocalizedNameStatusDTO(languageId);
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

    public ActivityCategory getById(int id) {
        return activityCategoryRepository.getById(id);
    }

    public ActivityCategory getByIdLocalized(int categoryId, int languageId) {
        return activityCategoryRepository.getByIdLocalized(categoryId, languageId);
    }

    public ActivityCategory getByNameNotSafe(String name, int languageId) {
        return activityCategoryRepository.getByNameNotSafe(name, languageId);
    }

    public ActivityCategory update(ActivityCategory activityCategory) {
        return activityCategoryRepository.update(activityCategory);
    }


}
