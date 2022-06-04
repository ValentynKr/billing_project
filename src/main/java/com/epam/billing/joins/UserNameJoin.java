package com.epam.billing.joins;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserNameJoin {

    String activityName;
    int userId;
    String userName;
    float activityDuration;

    @Override
    public String toString() {
        return "UserNameJoin{" +
                "activityName='" + activityName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", activityDuration=" + activityDuration +
                '}';
    }

    public String getActivityName() {
        return activityName;
    }

    public UserNameJoin setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserNameJoin setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserNameJoin setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public UserNameJoin setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
        return this;
    }
}
