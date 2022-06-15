package com.epam.billing.dto;

public class ActivityCategoryLocActivityUserActivityCountDTO {

    private int activityId;
    private int activityCategoryId;
    private String activityName;
    private String activityCategoryName;
    private int numberOfUserActivities;

    @Override
    public String toString() {
        return "ActivityCategoryLocActivityUserActivityCount{" +
                "activityId=" + activityId +
                ", activityCategoryId=" + activityCategoryId +
                ", activityName='" + activityName + '\'' +
                ", activityCategoryName='" + activityCategoryName + '\'' +
                ", numberOfUserActivities=" + numberOfUserActivities +
                '}';
    }

    public int getActivityId() {
        return activityId;
    }

    public ActivityCategoryLocActivityUserActivityCountDTO setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public int getActivityCategoryId() {
        return activityCategoryId;
    }

    public ActivityCategoryLocActivityUserActivityCountDTO setActivityCategoryId(int activityCategoryId) {
        this.activityCategoryId = activityCategoryId;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public ActivityCategoryLocActivityUserActivityCountDTO setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public String getActivityCategoryName() {
        return activityCategoryName;
    }

    public ActivityCategoryLocActivityUserActivityCountDTO setActivityCategoryName(String activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
        return this;
    }

    public int getNumberOfUserActivities() {
        return numberOfUserActivities;
    }

    public ActivityCategoryLocActivityUserActivityCountDTO setNumberOfUserActivities(int numberOfUserActivities) {
        this.numberOfUserActivities = numberOfUserActivities;
        return this;
    }
}
