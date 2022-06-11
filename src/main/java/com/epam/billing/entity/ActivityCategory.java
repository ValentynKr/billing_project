package com.epam.billing.entity;

public class ActivityCategory {

    private int categoryId;
    private ActivityCategoryStatus activityCategoryStatus;

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategory setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ActivityCategoryStatus getActivityCategoryStatus() {
        return activityCategoryStatus;
    }

    public ActivityCategory setActivityCategoryStatus(ActivityCategoryStatus activityCategoryStatus) {
        this.activityCategoryStatus = activityCategoryStatus;
        return this;
    }
}
