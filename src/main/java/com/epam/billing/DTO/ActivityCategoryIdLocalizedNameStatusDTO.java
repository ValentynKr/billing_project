package com.epam.billing.DTO;

import com.epam.billing.entity.ActivityCategoryStatus;

public class ActivityCategoryIdLocalizedNameStatusDTO {

    private int categoryId;
    private String categoryName;
    private ActivityCategoryStatus activityCategoryStatus;

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategoryIdLocalizedNameStatusDTO setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ActivityCategoryIdLocalizedNameStatusDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ActivityCategoryStatus getActivityCategoryStatus() {
        return activityCategoryStatus;
    }

    public ActivityCategoryIdLocalizedNameStatusDTO setActivityCategoryStatus(ActivityCategoryStatus activityCategoryStatus) {
        this.activityCategoryStatus = activityCategoryStatus;
        return this;
    }
}
