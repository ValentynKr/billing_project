package com.epam.billing.repository;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.UserRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository extends AbstractRepository<Activity>{

    private static final String SELECT_ALL = "SELECT * FROM activity";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM activity WHERE id = ?";
    private static final String SELECT_ID = "SELECT id FROM activity WHERE id = ?";
    private static final String INSERT = "INSERT INTO activity VALUES (DEFAULT, ?, ?)";
    private static final String DELETE = "DELETE FROM activity WHERE id = ?";
    private static final String UPDATE = "UPDATE activity SET category_id=?, name=? WHERE id = ?";

    @Override
    public List<Activity> getAll() {
        List<Activity> activityList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Activity activity = createEntity(resultSet);
                activityList.add(activity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityList;
    }

    @Override
    public Activity getById(long id) {
        Activity activity = new Activity();
        try (Connection connection = getConnection(); // toDo make try with resources for all repository methods
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activity.setActivityId(resultSet.getInt(1));
                activity.setCategoryOfActivityId(resultSet.getInt(2));
                activity.setName(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activity;
    }

    @Override
    public Activity save(Activity activity) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setInt(i++, activity.getActivityId());
            preparedStatement.setInt(i++, activity.getCategoryOfActivityId());
            preparedStatement.setString(i, activity.getName());

            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    activity.setActivityId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activity;
    }

    @Override
    public Activity update(Activity activity) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            int counter = 1;
            preparedStatement.setInt(counter++, activity.getCategoryOfActivityId());
            preparedStatement.setString(counter++, activity.getName());
            preparedStatement.setLong(counter, activity.getActivityId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activity;
    }

    @Override
    public boolean delete(Activity activity) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, activity.getActivityId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean existById(long id) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id") > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Activity createEntity(ResultSet resultSet) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(resultSet.getInt("id"));
        activity.setCategoryOfActivityId(resultSet.getInt("category_id"));
        activity.setName(resultSet.getString("name"));
        return activity;
    }
}
