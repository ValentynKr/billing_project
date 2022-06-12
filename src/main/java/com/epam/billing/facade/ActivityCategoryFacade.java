package com.epam.billing.facade;

import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.entity.ActivityCategoryDescription;
import com.epam.billing.service.ActivityCategoryDescriptionService;
import com.epam.billing.service.ActivityCategoryService;

import java.util.List;

public class ActivityCategoryFacade {

    public void addActivityCategoryWithDescription(ActivityCategory activityCategory,
                                                   List<String> activityCategoryDescriptionList,
                                                   ActivityCategoryService activityCategoryService,
                                                   ActivityCategoryDescriptionService activityCategoryDescriptionService) {
        ActivityCategory newActivityCategory = activityCategoryService.save(activityCategory);
        int newCategoryId = newActivityCategory.getCategoryId();
        ActivityCategoryDescription activityCategoryDescriptionEn = new ActivityCategoryDescription()
                .setCategoryId(newCategoryId)
                .setLanguageId(1)
                .setCategoryName(activityCategoryDescriptionList.get(0));
        ActivityCategoryDescription activityCategoryDescriptionRu = new ActivityCategoryDescription()
                .setCategoryId(newCategoryId)
                .setLanguageId(2)
                .setCategoryName(activityCategoryDescriptionList.get(1));
        activityCategoryDescriptionService.save(activityCategoryDescriptionEn);
        activityCategoryDescriptionService.save(activityCategoryDescriptionRu);
    }
}
