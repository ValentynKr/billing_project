package com.epam.billing.repository;

import com.epam.billing.entity.ActivityCategoryDescription;
import com.epam.billing.exeption.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityCategoryDescriptionRepository extends AbstractRepository<ActivityCategoryDescription>{


    private static final String SELECT_ALL = "SELECT * FROM activity_category_description";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM activity_category_description WHERE category_id = ?";
    private static final String INSERT = "INSERT INTO activity_category_description VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM activity_category_description WHERE category_id = ?";
    private static final String UPDATE = "UPDATE activity_category_description SET category_id=?, language_id=?, name=?";

    @Override
    public List<ActivityCategoryDescription> getAll() {
        List<ActivityCategoryDescription> activityCategoryDescriptionList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategoryDescription activityCategoryDescription = new ActivityCategoryDescription();
                activityCategoryDescription.setCategoryId(resultSet.getInt(1));
                activityCategoryDescription.setLanguageId(resultSet.getInt(2));
                activityCategoryDescription.setCategoryName(resultSet.getString(3));
                activityCategoryDescriptionList.add(activityCategoryDescription);
            }
        } catch (SQLException throwables) {
            throw new DBException();
        }
        return activityCategoryDescriptionList;
    }

    @Override
    public ActivityCategoryDescription getById(int categoryId) {
        ActivityCategoryDescription activityCategoryDescription = new ActivityCategoryDescription();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activityCategoryDescription.setCategoryId(resultSet.getInt(1));
                activityCategoryDescription.setLanguageId(resultSet.getInt(2));
                activityCategoryDescription.setCategoryName(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryDescription;
    }

    @Override
    public ActivityCategoryDescription save(ActivityCategoryDescription activityCategoryDescription) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            int i = 1;
            preparedStatement.setInt(i++, activityCategoryDescription.getCategoryId());
            preparedStatement.setInt(i++, activityCategoryDescription.getLanguageId());
            preparedStatement.setString(i, activityCategoryDescription.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryDescription;
    }

    @Override
    public ActivityCategoryDescription update(ActivityCategoryDescription activityCategoryDescription) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int counter = 1;
            preparedStatement.setInt(counter++, activityCategoryDescription.getCategoryId());
            preparedStatement.setInt(counter++, activityCategoryDescription.getLanguageId());
            preparedStatement.setString(counter, activityCategoryDescription.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryDescription;
    }

    @Override
    public boolean delete(ActivityCategoryDescription activityCategoryDescription) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, activityCategoryDescription.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean existById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
