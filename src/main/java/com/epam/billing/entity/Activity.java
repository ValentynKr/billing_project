package com.epam.billing.entity;

public class Activity {

    private int activityId;
    private String name;
    private int categoryOfActivityId;

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", categoryOfActivityId=" + categoryOfActivityId +
                '}';
    }

    public int getActivityId() {
        return activityId;
    }

    public Activity setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public int getCategoryOfActivityId() {
        return categoryOfActivityId;
    }

    public Activity setCategoryOfActivityId(int categoryOfActivityId) {
        this.categoryOfActivityId = categoryOfActivityId;
        return this;
    }
}
