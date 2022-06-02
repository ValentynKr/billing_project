package com.epam.billing.utils;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.repository.ActivityRepository;
import com.epam.billing.service.ActivityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataConverter {

    private DataConverter() {}

    public static HashMap<String,Integer> userActivityConverter(List<UserActivity> userActivityList) {
        HashMap<String,Integer> convertedResult = new HashMap<>();
        ActivityRepository activityRepository = new ActivityRepository();
        for (UserActivity userActivity: userActivityList) {
            Activity activity = activityRepository.getById(userActivity.getActivityId());
            if (convertedResult.containsKey(activity.getName())) {
                int integer = convertedResult.get(activity.getName());
                convertedResult.put(activity.getName(), integer + userActivity.getDurationOfActivity());
            } else {
                convertedResult.put(activity.getName(), userActivity.getDurationOfActivity());
            }
        }
        return convertedResult;
    }

}
