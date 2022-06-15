package com.epam.billing.repository;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.UserActivity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserActivityRepository extends AbstractRepository<UserActivity> {

    private static final String SELECT_ALL = "SELECT * FROM user_activities";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM user_activities WHERE id = ?";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM user_activities WHERE user_id = ?";
    private static final String SELECT_BY_ACTIVITY_ID = "SELECT * FROM user_activities WHERE activity_id = ?";
    private static final String SELECT_BY_ACTIVITY_ID_AND_USER_ID = "SELECT * FROM user_activities WHERE activity_id = ? AND user_id = ?";
    private static final String EXIST_BY_ID = "SELECT * FROM user_activities WHERE EXISTS (SELECT * " +
            "FROM user_activities WHERE id = ?)";
    private static final String INSERT = "INSERT INTO user_activities VALUES (DEFAULT, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM user_activities WHERE id = ?";
    private static final String UPDATE = "UPDATE user_activities SET activity_id=?, user_id=?, duration=?" +
            "WHERE id=?";
    private static final String GET_USER_ACTIVITY_NAMED = "SELECT\n" +
            "user_activities.id, activity_category_description.name, activity.name, users.name , user_activities.duration\n" +
            "FROM  user_activities\n" +
            "inner JOIN activity\n" +
            "on user_activities.activity_id = activity.id\n" +
            "inner join users\n" +
            "on user_activities.user_id = users.id\n" +
            "inner join activity_category\n" +
            "on activity.category_id = activity_category.id\n" +
            "inner join activity_category_description\n" +
            "on activity_category.id = activity_category_description.category_id\n" +
            "where user_activities.id=? and language_id =?";

    private static final String JOIN_USER_NAME = "SELECT\n" +
            "activity_category_description.name, activity.name, users.name , user_activities.duration\n" +
            "FROM  user_activities\n" +
            "inner JOIN activity\n" +
            "on user_activities.activity_id = activity.id\n" +
            "inner join users\n" +
            "on user_activities.user_id = users.id\n" +
            "inner join activity_category\n" +
            "on activity.category_id = activity_category.id\n" +
            "inner join activity_category_description\n" +
            "on activity_category.id = activity_category_description.category_id\n" +
            "where user_id=? and language_id =?\n" +
            "order by activity_category_description.name";

    private static final String JOIN_ALL = "SELECT\n" +
            "activity_category_description.name, activity.name, users.name , user_activities.duration\n" +
            "FROM  user_activities\n" +
            "inner JOIN activity\n" +
            "on user_activities.activity_id = activity.id\n" +
            "inner join users\n" +
            "on user_activities.user_id = users.id\n" +
            "inner join activity_category\n" +
            "on activity.category_id = activity_category.id\n" +
            "inner join activity_category_description\n" +
            "on activity_category.id = activity_category_description.category_id\n" +
            "where language_id =? order by users.name";


    @Override
    public List<UserActivity> getAll() {
        List<UserActivity> userActivityList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserActivity userActivity = createEntity(resultSet); // toDo refactoring: make Class only once
                userActivityList.add(userActivity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityList;
    }

    @Override
    public UserActivity getById(int id) {
        UserActivity userActivity = new UserActivity();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivity.setUserActivityId(resultSet.getInt(1));
                userActivity.setActivityId(resultSet.getInt(2));
                userActivity.setUserId(resultSet.getInt(3));
                userActivity.setDurationOfActivity(resultSet.getFloat(4));
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
            preparedStatement.setInt(i++, userActivity.getActivityId());
            preparedStatement.setInt(i++, userActivity.getUserId());
            preparedStatement.setFloat(i, userActivity.getDurationOfActivity());
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
            preparedStatement.setFloat(counter++, userActivity.getDurationOfActivity());
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
    public boolean existById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<UserActivity> getByUserId(int userId) {
        List<UserActivity> userActivityList = new ArrayList<>();
        UserActivity userActivity;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivity = createEntity(resultSet);
                userActivityList.add(userActivity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityList;
    }

    public List<UserActivity> getUserActivityByActivityId(int activityId) {
        List<UserActivity> userActivityList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ACTIVITY_ID)) {
            preparedStatement.setInt(1, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivityList.add(createEntity(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityList;
    }

    public UserActivity getByActivityIdAndUserId(int activityId, int userId) {
       UserActivity userActivity = new UserActivity();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ACTIVITY_ID_AND_USER_ID)) {
            preparedStatement.setInt(1, activityId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivity = createEntity(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivity;
    }

    public List<UserActivityUserNameIdDurationRecordingDTO> getUserActivityUserNameIdDurationDTO(int userId, int languageId) {
        List<UserActivityUserNameIdDurationRecordingDTO> userActivityUserNameIdDurationRecordingDTOS = new ArrayList<>();
        UserActivityUserNameIdDurationRecordingDTO userActivityUserNameIdDurationRecordingDTO;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(JOIN_USER_NAME)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivityUserNameIdDurationRecordingDTO = new UserActivityUserNameIdDurationRecordingDTO();
                userActivityUserNameIdDurationRecordingDTO.setActivityCategoryName(resultSet.getString(1));
                userActivityUserNameIdDurationRecordingDTO.setActivityName(resultSet.getString(2));
                userActivityUserNameIdDurationRecordingDTO.setUserName(resultSet.getString(3));
                userActivityUserNameIdDurationRecordingDTO.setActivityDuration(resultSet.getFloat(4));
                userActivityUserNameIdDurationRecordingDTOS.add(userActivityUserNameIdDurationRecordingDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityUserNameIdDurationRecordingDTOS;
    }

    public UserActivityUserNameIdDurationRecordingDTO getByIdUserActivityUserNameIdDurationDTO(int userActivityId, int languageId) {
        UserActivityUserNameIdDurationRecordingDTO userActivityUserNameIdDurationRecordingDTO = new
                UserActivityUserNameIdDurationRecordingDTO();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ACTIVITY_NAMED)) {
            preparedStatement.setInt(1, userActivityId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivityUserNameIdDurationRecordingDTO = new UserActivityUserNameIdDurationRecordingDTO();
                userActivityUserNameIdDurationRecordingDTO.setUserActivityId(resultSet.getInt(1));
                userActivityUserNameIdDurationRecordingDTO.setActivityCategoryName(resultSet.getString(2));
                userActivityUserNameIdDurationRecordingDTO.setActivityName(resultSet.getString(3));
                userActivityUserNameIdDurationRecordingDTO.setUserName(resultSet.getString(4));
                userActivityUserNameIdDurationRecordingDTO.setActivityDuration(resultSet.getFloat(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityUserNameIdDurationRecordingDTO;
    }

    public List<UserActivityUserNameIdDurationRecordingDTO> getAllUserActivitiesDurationDTO(int languageId) {
        List<UserActivityUserNameIdDurationRecordingDTO> userActivityUserNameIdDurationRecordingDTOS = new ArrayList<>();
        UserActivityUserNameIdDurationRecordingDTO userActivityUserNameIdDurationRecordingDTO;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(JOIN_ALL)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userActivityUserNameIdDurationRecordingDTO = new UserActivityUserNameIdDurationRecordingDTO();
                userActivityUserNameIdDurationRecordingDTO.setActivityCategoryName(resultSet.getString(1));
                userActivityUserNameIdDurationRecordingDTO.setActivityName(resultSet.getString(2));
                userActivityUserNameIdDurationRecordingDTO.setUserName(resultSet.getString(3));
                userActivityUserNameIdDurationRecordingDTO.setActivityDuration(resultSet.getFloat(4));
                userActivityUserNameIdDurationRecordingDTOS.add(userActivityUserNameIdDurationRecordingDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userActivityUserNameIdDurationRecordingDTOS;
    }

    private UserActivity createEntity(ResultSet resultSet) throws SQLException {
        UserActivity userActivity = new UserActivity();
        userActivity.setUserActivityId(resultSet.getInt("id"));
        userActivity.setActivityId(resultSet.getInt("activity_id"));
        userActivity.setUserId(resultSet.getInt("user_id"));
        userActivity.setDurationOfActivity(resultSet.getFloat("duration"));
        return userActivity;
    }
}
