package com.epam.billing.repository;

import com.epam.billing.dto.DateStatusTypeUserRequestDTO;
import com.epam.billing.entity.RequestStatus;
import com.epam.billing.entity.RequestType;
import com.epam.billing.entity.UserRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRequestRepository extends AbstractRepository<UserRequest> {

    private static final String SELECT_ALL = "SELECT * FROM user_request";
    private static final String SELECT_ALL_WITH_NAMES = "SELECT\n" +
            "user_request.id, user_request.request_date, user_request.request_status, user_request.request_type, users.name  \n" +
            "FROM  user_request\n" +
            "INNER JOIN users\n" +
            "ON user_request.user_id = users.id \n" +
            "ORDER BY CASE WHEN request_status = 'UNRESOLVED' THEN 0 ELSE 1 END, request_date DESC";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM user_request WHERE id = ?";
    private static final String SELECT_UNRESOLVED_EDIT_REQUESTS = "SELECT * FROM user_request WHERE user_id = ? AND activity_id = ? AND request_type = 'EDIT' AND request_status = 'UNRESOLVED'";
    private static final String EXIST_BY_ID = "SELECT * FROM user_request WHERE EXISTS(SELECT * FROM user_request WHERE id = ?)";
    private static final String INSERT = "INSERT INTO user_request VALUES (DEFAULT, DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM user_request WHERE id = ?";
    private static final String UPDATE = "UPDATE user_request SET request_date=?, user_id=?, request_type=?, request_status=?, " +
            "activity_category_id=?, activity_id=?, user_activity_duration=?, new_activity_name=?, comment=?" +
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

    public boolean areUnresolvedEditRequestsPresent(int userId, int activityId) {
        boolean isPresent = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_UNRESOLVED_EDIT_REQUESTS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                isPresent = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isPresent;
    }

    public List<DateStatusTypeUserRequestDTO> getAllWithUserNames() {
        List<DateStatusTypeUserRequestDTO> dateStatusTypeUserRequestDTOList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WITH_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DateStatusTypeUserRequestDTO dateStatusTypeUserRequestDTO = new DateStatusTypeUserRequestDTO();
                dateStatusTypeUserRequestDTO.setRequestId(resultSet.getInt(1));
                dateStatusTypeUserRequestDTO.setTimestamp(resultSet.getTimestamp(2));
                dateStatusTypeUserRequestDTO.setRequestStatus(RequestStatus.valueOf(resultSet.getString(3)));
                dateStatusTypeUserRequestDTO.setRequestType(RequestType.valueOf(resultSet.getString(4)));
                dateStatusTypeUserRequestDTO.setUserName(resultSet.getString(5));
                dateStatusTypeUserRequestDTOList.add(dateStatusTypeUserRequestDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dateStatusTypeUserRequestDTOList;
    }

    @Override
    public UserRequest getById(int id) {
        UserRequest userRequest = new UserRequest();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userRequest.setUserRequestId(resultSet.getInt(1));
                userRequest.setTimestamp(resultSet.getTimestamp(2));
                userRequest.setUserId(resultSet.getInt(3));
                userRequest.setRequestType(RequestType.valueOf(resultSet.getString(4)));
                userRequest.setRequestStatus(RequestStatus.valueOf(resultSet.getString(5)));
                userRequest.setActivityCategoryId(resultSet.getInt(6));
                userRequest.setActivityId(resultSet.getInt(7));
                userRequest.setUserActivityDuration(resultSet.getFloat(8));
                userRequest.setNewActivityName(resultSet.getString(9));
                userRequest.setComment(resultSet.getString(10));
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
            preparedStatement.setInt(i++, userRequest.getUserId());
            preparedStatement.setString(i++, userRequest.getRequestType().toString());
            preparedStatement.setString(i++, userRequest.getRequestStatus().toString());
            preparedStatement.setInt(i++, userRequest.getActivityCategoryId());
            preparedStatement.setInt(i++, userRequest.getActivityId());
            preparedStatement.setFloat(i++, userRequest.getUserActivityDuration());
            preparedStatement.setString(i++, userRequest.getNewActivityName());
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
            preparedStatement.setTimestamp(counter++, userRequest.getTimestamp());
            preparedStatement.setInt(counter++, userRequest.getUserId());
            preparedStatement.setString(counter++, userRequest.getRequestType().toString());
            preparedStatement.setString(counter++, userRequest.getRequestStatus().toString());
            preparedStatement.setInt(counter++, userRequest.getActivityCategoryId());
            preparedStatement.setInt(counter++, userRequest.getActivityId());
            preparedStatement.setFloat(counter++, userRequest.getUserActivityDuration());
            preparedStatement.setString(counter++, userRequest.getNewActivityName());
            preparedStatement.setString(counter++, userRequest.getComment());
            preparedStatement.setInt(counter, userRequest.getUserRequestId());
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

    private UserRequest createEntity(ResultSet resultSet) throws SQLException {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserRequestId(resultSet.getInt(1));
        userRequest.setTimestamp(resultSet.getTimestamp(2));
        userRequest.setUserId(resultSet.getInt(3));
        userRequest.setRequestType(RequestType.valueOf(resultSet.getString(4)));
        userRequest.setRequestStatus(RequestStatus.valueOf(resultSet.getString(5)));
        userRequest.setActivityCategoryId(resultSet.getInt(6));
        userRequest.setActivityId(resultSet.getInt(7));
        userRequest.setUserActivityDuration(resultSet.getFloat(8));
        userRequest.setNewActivityName(resultSet.getString(9));
        userRequest.setComment(resultSet.getString(10));
        return userRequest;
    }
}
