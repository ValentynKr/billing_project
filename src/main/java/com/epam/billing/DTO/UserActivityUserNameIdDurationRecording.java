package com.epam.billing.DTO;


import com.epam.billing.repository.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserActivityUserNameIdDurationRecording {

    private static final String JOIN_USER_NAME =
            "SELECT \n" +
                    "activity_category.name, activity.name, users.name , user_activities.duration \n" +
                    "FROM  user_activities\n" +
                    "inner JOIN activity\n" +
                    "on user_activities.activity_id = activity.id\n" +
                    "inner join users\n" +
                    "on user_activities.user_id = users.id\n" +
                    "inner join activity_category\n" +
                    "on activity.category_id = activity_category.id\n" +
                    "where user_id=?";


    String activityCategoryName;
    String activityName;
    String userName;
    float activityDuration;


    private final ConnectionManager connectionManager = new ConnectionManager();
    protected Connection getConnection() {
        return connectionManager.getConnection();
    }

    public String getActivityCategoryName() {
        return activityCategoryName;
    }

    public UserActivityUserNameIdDurationRecording setActivityCategoryName(String activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
        return this;
    }

    public String getActivityName() {
        return activityName;
    }

    public UserActivityUserNameIdDurationRecording setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserActivityUserNameIdDurationRecording setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public float getActivityDuration() {
        return activityDuration;
    }

    public UserActivityUserNameIdDurationRecording setActivityDuration(float activityDuration) {
        this.activityDuration = activityDuration;
        return this;
    }

    public List<UserActivityUserNameIdDurationRecording> getUserActivityUserNameIdDurationDTO(int userId) {
        List<UserActivityUserNameIdDurationRecording> userActivityUserNameIdDurationRecordings = new ArrayList<>();
        UserActivityUserNameIdDurationRecording userActivityUserNameIdDurationRecording;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(JOIN_USER_NAME)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivityUserNameIdDurationRecording = new UserActivityUserNameIdDurationRecording();
                userActivityUserNameIdDurationRecording.setActivityCategoryName(resultSet.getString(1));
                userActivityUserNameIdDurationRecording.setActivityName(resultSet.getString(2));
                userActivityUserNameIdDurationRecording.setUserName(resultSet.getString(3));
                userActivityUserNameIdDurationRecording.setActivityDuration(resultSet.getFloat(4));
                userActivityUserNameIdDurationRecordings.add(userActivityUserNameIdDurationRecording);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityUserNameIdDurationRecordings;
    }
}
