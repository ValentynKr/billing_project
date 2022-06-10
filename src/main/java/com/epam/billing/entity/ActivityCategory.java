package com.epam.billing.entity;

public class ActivityCategory {

    private int categoryId;
    private String categoryName;
    private ActivityCategoryStatus activityCategoryStatus;

    @Override
    public String toString() {
        return "ActivityCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategory setCategoryId(int categoryOfActivityId) {
        this.categoryId = categoryOfActivityId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ActivityCategory setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
