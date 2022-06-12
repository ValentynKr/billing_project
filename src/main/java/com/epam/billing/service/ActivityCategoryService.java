package com.epam.billing.service;

import com.epam.billing.dto.ActivityCategoryIdLocalizedNameDTO;
import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.repository.ActivityCategoryRepository;

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

    public List<ActivityCategoryIdLocalizedNameDTO> getAllWithLocalizedNames(int languageId) {
        return activityCategoryRepository.getAllWithLocalizedNames(languageId);
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getOpenedWithLocalizedNames(int languageId) {
        return activityCategoryRepository.getOpenedWithLocalizedNames(languageId);
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getAllWithLocalizedNameStatusDTO(int languageId) {
        return activityCategoryRepository.getAllWithLocalizedNameStatusDTO(languageId);
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getByIdWithDescriptions(int categoryId) {
        return activityCategoryRepository.getByIdWithDescriptions(categoryId);
    }

    public ActivityCategory save(ActivityCategory activityCategory) {
        return activityCategoryRepository.save(activityCategory);
    }

    public boolean delete(ActivityCategory activityCategory) {
        return activityCategoryRepository.delete(activityCategory);
    }

    public boolean existById(int id) {
        return activityCategoryRepository.existById(id);
    }

    public ActivityCategory getById(int id) {
        return activityCategoryRepository.getById(id);
    }

    public ActivityCategoryIdLocalizedNameStatusDTO getByIdLocalized(int categoryId, int languageId) {
        return activityCategoryRepository.getByIdLocalized(categoryId, languageId);
    }

    public ActivityCategoryIdLocalizedNameStatusDTO getByNameNotSafe(String name, int languageId) {
        return activityCategoryRepository.getByNameNotSafe(name, languageId);
    }

    public Optional<ActivityCategoryIdLocalizedNameStatusDTO> getByNameSafe(String name, int languageId) {
        return activityCategoryRepository.getByNameSafe(name, languageId);
    }

    public ActivityCategory update(ActivityCategory activityCategory) {
        return activityCategoryRepository.update(activityCategory);
    }


}
