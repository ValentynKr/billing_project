package com.epam.billing.dto;

public class UserActivityUserNameIdDurationRecordingDTO {

    private int userActivityId;
    private String activityCategoryName;
    private String activityName;
    private String userName;
    private float activityDuration;

    @Override
    public String toString() {
        return "UserActivityUserNameIdDurationRecordingDTO{" +
                "userActivityId=" + userActivityId +
                ", activityCategoryName='" + activityCategoryName + '\'' +
                ", activityName='" + activityName + '\'' +
                ", userName='" + userName + '\'' +
                ", activityDuration=" + activityDuration +
                '}';
    }

    public int getUserActivityId() {
        return userActivityId;
    }

    public UserActivityUserNameIdDurationRecordingDTO setUserActivityId(int userActivityId) {
        this.userActivityId = userActivityId;
        return this;
    }

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
