package com.epam.billing.repository;

import com.epam.billing.entity.ActivityCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityCategoryRepository extends AbstractRepository<ActivityCategory> {

    private static final String SELECT_ALL = "SELECT * FROM activity_category";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM activity_category WHERE id = ?";
    private static final String SELECT_ID = "SELECT id FROM activity_category WHERE id = ?";
    private static final String INSERT_ACTIVITY_CATEGORY = "INSERT INTO activity_category VALUES (DEFAULT, ?)";
    private static final String DELETE_ACTIVITY_CATEGORY = "DELETE FROM activity_category WHERE id = ?";
    private static final String UPDATE = "UPDATE activity_category SET name=? " +
            "WHERE id=?";

    @Override
    public List<ActivityCategory> getAll() {
        List<ActivityCategory> activityCategoryList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategory activityCategory = createEntity(resultSet);
                activityCategoryList.add(activityCategory);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryList;
    }

    @Override
    public ActivityCategory getById(long id) {
        ActivityCategory activityCategory = new ActivityCategory();
        try (Connection connection = getConnection(); // toDo make try with resources for all repository methods
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activityCategory.setCategoryId(resultSet.getInt(1));
                activityCategory.setCategotyName(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategory;
    }

    @Override
    public ActivityCategory save(ActivityCategory activityCategory) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACTIVITY_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setInt(i++, activityCategory.getCategoryId());
            preparedStatement.setString(i, activityCategory.getCategotyName());

            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    activityCategory.setCategoryId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategory;
    }

    @Override
    public ActivityCategory update(ActivityCategory activityCategory) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            int counter = 1;
            preparedStatement.setString(counter++, activityCategory.getCategotyName());
            preparedStatement.setLong(counter, activityCategory.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategory;
    }

    @Override
    public boolean delete(ActivityCategory activityCategory) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACTIVITY_CATEGORY);
            preparedStatement.setInt(1, activityCategory.getCategoryId());
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

    private ActivityCategory createEntity(ResultSet resultSet) throws SQLException {
        ActivityCategory activityCategory = new ActivityCategory();
        activityCategory.setCategoryId(resultSet.getInt("id"));
        activityCategory.setCategotyName(resultSet.getString("name"));
        return activityCategory;
    }
}
