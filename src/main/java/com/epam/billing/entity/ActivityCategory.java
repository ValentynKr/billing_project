package com.epam.billing.entity;

public class ActivityCategory {

    private int categoryId;
    private String categotyName;

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategory setCategoryId(int categoryOfActivityId) {
        this.categoryId = categoryOfActivityId;
        return this;
    }

    public String getCategotyName() {
        return categotyName;
    }

    public ActivityCategory setCategotyName(String categotyName) {
        this.categotyName = categotyName;
        return this;
    }
}
