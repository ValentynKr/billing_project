package com.epam.billing.DTO;

public class UserActivityUserNameIdDurationRecordingDTO {

    private String activityCategoryName;
    private String activityName;
    private String userName;
    private float activityDuration;


    public String getActivityCategoryName() {
        return activityCategoryName;
    }

    public UserActivityUserNameIdDurationRecordingDTO setActivityCategoryName(String activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public UserActivityUserNameIdDurationRecordingDTO setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserActivityUserNameIdDurationRecordingDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public UserActivityUserNameIdDurationRecordingDTO setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
        return this;
    }
}
