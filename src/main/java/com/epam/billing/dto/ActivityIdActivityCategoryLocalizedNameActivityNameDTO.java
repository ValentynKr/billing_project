package com.epam.billing.dto;

public class ActivityIdActivityCategoryLocalizedNameActivityNameDTO {

    private int activityId;
    private String activityCategoryLocalizedName;
    private String activityName;

    public int getActivityId() {
        return activityId;
    }

    public ActivityIdActivityCategoryLocalizedNameActivityNameDTO setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getActivityCategoryLocalizedName() {
        return activityCategoryLocalizedName;
    }

    public ActivityIdActivityCategoryLocalizedNameActivityNameDTO setActivityCategoryLocalizedName(String activityCategoryLocalizedName) {
        this.activityCategoryLocalizedName = activityCategoryLocalizedName;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public ActivityIdActivityCategoryLocalizedNameActivityNameDTO setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }
}
