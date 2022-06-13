package com.epam.billing.service;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.repository.UserActivityRepository;

import java.util.List;

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

    public boolean existById(int id) {
        return userActivityRepository.existById(id);
    }

    public UserActivity getById(int id) {
        return userActivityRepository.getById(id);
    }

    public UserActivity update(UserActivity userActivity) {
        return userActivityRepository.update(userActivity);
    }

    public List<UserActivity> getByUserId(int id) {
        return userActivityRepository.getByUserId(id);
    }

    public List<UserActivity> getByActivityId(int activityId) {
        return userActivityRepository.getUserActivityByActivityId(activityId);
    }
    public UserActivity getByActivityIdAndUserId(int activityId, int userId) {
        return userActivityRepository.getByActivityIdAndUserId(activityId, userId);
    }

    public List<UserActivityUserNameIdDurationRecordingDTO> getUserActivityUserNameIdDurationDTO(int activityId, int languageId) {
        return userActivityRepository.getUserActivityUserNameIdDurationDTO(activityId, languageId);
    }
    public List<UserActivityUserNameIdDurationRecordingDTO> getAllUserActivitiesDurationDTO(int languageId) {
        return userActivityRepository.getAllUserActivitiesDurationDTO(languageId);
    }
    public UserActivityUserNameIdDurationRecordingDTO getByIdUserActivityUserNameIdDurationDTO(int activityId, int languageId) {
        return userActivityRepository.getByIdUserActivityUserNameIdDurationDTO(activityId, languageId);
    }


}
