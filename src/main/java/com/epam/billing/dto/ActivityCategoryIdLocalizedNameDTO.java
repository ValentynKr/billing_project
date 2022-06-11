package com.epam.billing.dto;

public class ActivityCategoryIdLocalizedNameDTO {

    private int categoryId;
    private String categoryName;

    @Override
    public String toString() {
        return "ActivityCategoryIdLocalizedName{" +
                "categoryId=" + categoryId +
                ", name='" + categoryName + '\'' +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public ActivityCategoryIdLocalizedNameDTO setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ActivityCategoryIdLocalizedNameDTO setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
