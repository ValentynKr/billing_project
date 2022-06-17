package com.epam.billing.entity;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;

import java.util.List;

public class UserActivityReport {
    private List<UserActivityUserNameIdDurationRecordingDTO> listOfUserActivity;
    private String userName;
    private float totalTimeSpent;

    public List<UserActivityUserNameIdDurationRecordingDTO> getListOfUserActivity() {
        return listOfUserActivity;
    }

    public UserActivityReport setListOfUserActivity(List<UserActivityUserNameIdDurationRecordingDTO> listOfUserActivity) {
        this.listOfUserActivity = listOfUserActivity;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserActivityReport setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public float getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public UserActivityReport setTotalTimeSpent(float totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
        return this;
    }
}
