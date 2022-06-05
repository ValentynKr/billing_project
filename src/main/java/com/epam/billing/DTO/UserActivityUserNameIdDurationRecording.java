package com.epam.billing.DTO;

public class UserActivityUserNameIdDurationRecording {

    private String activityCategoryName;
    private String activityName;
    private String userName;
    private float activityDuration;


    public String getActivityCategoryName() {
        return activityCategoryName;
    }

    public UserActivityUserNameIdDurationRecording setActivityCategoryName(String activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public UserActivityUserNameIdDurationRecording setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserActivityUserNameIdDurationRecording setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public UserActivityUserNameIdDurationRecording setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
        return this;
    }
}
