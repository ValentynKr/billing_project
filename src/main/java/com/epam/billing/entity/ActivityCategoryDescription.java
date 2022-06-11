package com.epam.billing.entity;

public class ActivityCategoryDescription {

    private int categoryId;
    private int languageId;
    private String categoryName;

    @Override
    public String toString() {
        return "ActivityCategoryDescription{" +
                "categoryId=" + categoryId +
                ", languageId=" + languageId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategoryDescription setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public int getLanguageId() {
        return languageId;
    }

    public ActivityCategoryDescription setLanguageId(int languageId) {
        this.languageId = languageId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ActivityCategoryDescription setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
