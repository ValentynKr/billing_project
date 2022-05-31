package com.epam.billing.repository;

import com.epam.billing.entity.UserRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRequestRepository extends AbstractRepository<UserRequest> {

    private static final String SELECT_ALL = "SELECT * FROM user_request";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM user_request WHERE id = ?";
    private static final String EXIST_BY_ID = "SELECT * FROM user_request WHERE EXISTS(SELECT * FROM user_request WHERE id = ?)";
    private static final String INSERT = "INSERT INTO user_request VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM user_request WHERE id = ?";
    private static final String UPDATE = "UPDATE user_request SET user_id=?, request_type=?, request_status=?, " +
            "activity_id=?, new_activity_name=?, comment=?" +
            "WHERE id=?";

    @Override
    public List<UserRequest> getAll() {
        List<UserRequest> userRequestList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRequest userRequest = createEntity(resultSet);
                userRequestList.add(userRequest);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRequestList;
    }

    @Override
    public UserRequest getById(long id) {
        UserRequest userRequest = new UserRequest();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userRequest.setUserRequestId(resultSet.getInt(1));
                userRequest.setUserId(resultSet.getInt(2));
                userRequest.setRequestType(resultSet.getString(3));
                userRequest.setRequestStatus(resultSet.getString(4));
                userRequest.setActivityId(resultSet.getInt(5));
                userRequest.setNewActivityName(resultSet.getString(6));
                userRequest.setComment(resultSet.getString(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRequest;
    }

    @Override
    public UserRequest save(UserRequest userRequest) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setInt(i++, userRequest.getUserRequestId());
            i = insertDataFromEntity(userRequest, preparedStatement, i);
            preparedStatement.setString(i, userRequest.getComment());

            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    userRequest.setUserRequestId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRequest;
    }

    @Override
    public UserRequest update(UserRequest userRequest) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int counter = 1;
            counter = insertDataFromEntity(userRequest, preparedStatement, counter);
            preparedStatement.setString(counter++, userRequest.getComment());
            preparedStatement.setLong(counter, userRequest.getUserRequestId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userRequest;
    }

    @Override
    public boolean delete(UserRequest userRequest) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, userRequest.getUserRequestId());
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

    private UserRequest createEntity(ResultSet resultSet) throws SQLException {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserRequestId(resultSet.getInt("id"));
        userRequest.setUserId(resultSet.getInt("user_id"));
        userRequest.setRequestType(resultSet.getString("request_type"));
        userRequest.setRequestStatus(resultSet.getString("request_status"));
        userRequest.setActivityId(resultSet.getInt("activity_id"));
        userRequest.setNewActivityName(resultSet.getString("new_activity_name"));
        userRequest.setComment(resultSet.getString("comment"));
        return userRequest;
    }

    private int insertDataFromEntity(UserRequest userRequest, PreparedStatement preparedStatement, int i) throws SQLException {
        preparedStatement.setInt(i++, userRequest.getUserId());
        preparedStatement.setString(i++, userRequest.getRequestType());
        preparedStatement.setString(i++, userRequest.getRequestStatus());
        preparedStatement.setInt(i++, userRequest.getActivityId());
        preparedStatement.setString(i++, userRequest.getNewActivityName());
        return i;
    }
}
