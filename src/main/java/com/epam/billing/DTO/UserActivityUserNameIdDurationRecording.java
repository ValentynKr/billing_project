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
                    "activity.name, user_activities.user_id, users.name , user_activities.duration \n" +
                    "FROM  user_activities\n" +
                    "inner JOIN activity\n" +
                    "on user_activities.activity_id = activity.id\n" +
                    "inner join users\n" +
                    "on user_activities.user_id = users.id\n" +
                    "where user_id=?";
    String activityName;
    int userId;
    String userName;
    float activityDuration;


    private final ConnectionManager connectionManager = new ConnectionManager();
    protected Connection getConnection() {
        return connectionManager.getConnection();
    }

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

    public UserActivityUserNameIdDurationRecording setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserActivityUserNameIdDurationRecording setUserId(int userId) {
        this.userId = userId;
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
                userActivityUserNameIdDurationRecording.setActivityName(resultSet.getString(1));
                userActivityUserNameIdDurationRecording.setUserId(resultSet.getInt(2));
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
