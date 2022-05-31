package com.epam.billing.repository;

import com.epam.billing.entity.UserActivity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserActivityRepository extends AbstractRepository<UserActivity> {

    private static final String SELECT_ALL = "SELECT * FROM user_activities";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM user_activities WHERE id = ?";
    private static final String EXIST_BY_ID = "SELECT * FROM user_activities WHERE EXISTS (SELECT * " +
            "FROM user_activities WHERE id = ?)";
    private static final String INSERT = "INSERT INTO user_activities VALUES (DEFAULT, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM user_activities WHERE id = ?";
    private static final String UPDATE = "UPDATE user_activities SET activity_id=?, user_id=?, duration=?" +
            "WHERE id=?";

    @Override
    public List<UserActivity> getAll() {
        List<UserActivity> userActivityList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserActivity userActivity = createEntity(resultSet);
                userActivityList.add(userActivity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityList;
    }

    @Override
    public UserActivity getById(long id) {
        UserActivity userActivity = new UserActivity();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivity.setUserActivityId(resultSet.getInt(1));
                userActivity.setActivityId(resultSet.getInt(2));
                userActivity.setUserId(resultSet.getInt(3));
                userActivity.setDurationOfActivity(resultSet.getInt(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivity;
    }

    @Override
    public UserActivity save(UserActivity userActivity) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setInt(i++, userActivity.getUserActivityId());
            preparedStatement.setInt(i++, userActivity.getActivityId());
            preparedStatement.setInt(i++, userActivity.getUserId());
            preparedStatement.setInt(i, userActivity.getDurationOfActivity());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    userActivity.setUserActivityId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivity;
    }

    @Override
    public UserActivity update(UserActivity userActivity) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int counter = 1;
            preparedStatement.setInt(counter++, userActivity.getActivityId());
            preparedStatement.setInt(counter++, userActivity.getUserId());
            preparedStatement.setInt(counter++, userActivity.getDurationOfActivity());
            preparedStatement.setLong(counter, userActivity.getUserActivityId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivity;
    }

    @Override
    public boolean delete(UserActivity userActivity) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, userActivity.getUserActivityId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean existById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private UserActivity createEntity(ResultSet resultSet) throws SQLException {
        UserActivity userActivity = new UserActivity();
        userActivity.setUserActivityId(resultSet.getInt("id"));
        userActivity.setActivityId(resultSet.getInt("activity_id"));
        userActivity.setUserActivityId(resultSet.getInt("user_id"));
        userActivity.setDurationOfActivity(resultSet.getInt("duration"));
        return userActivity;
    }
}
