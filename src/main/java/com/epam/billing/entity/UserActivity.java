package com.epam.billing.entity;

public class UserActivity {

    private int userActivityId;
    private int activityId;
    private int userId;
    private float durationOfActivity;

    public String toString() {

        return userActivityId + ". " +
                activityId + " " +
                userId + " " +
                durationOfActivity;
    }

    public int getUserActivityId() {
        return userActivityId;
    }

    public UserActivity setUserActivityId(int userActivityId) {
        this.userActivityId = userActivityId;
        return this;
    }

    public int getActivityId() {
        return activityId;
    }

    public UserActivity setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserActivity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public float getDurationOfActivity() {
        return durationOfActivity;
    }

    public UserActivity setDurationOfActivity(float durationOfActivity) {
        this.durationOfActivity = durationOfActivity;
        return this;
    }
}
