package com.epam.billing.repository;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<User> {

    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_WHERE_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SELECT_ID = "SELECT id FROM users WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE = "UPDATE users SET name=?, admin=?, email=?, pass=? " +
            "WHERE id=?";


    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = createEntity(resultSet);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throw new DBException();
        }
        return userList;
    }

    public Optional<User> getByEmail(String email) {
        Connection connection = getConnection();
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                user = new User();
                while (resultSet.next()) {
                    user.setUserId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setAdmin(resultSet.getBoolean(3));
                    user.setEmail(resultSet.getString(4));
                    user.setPassword(resultSet.getString(5));
                }
            }
        } catch (SQLException throwables) {
            throw new DBException();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User getById(long id) {

        User user = new User();
        try (Connection connection = getConnection(); // toDo make try with resources for all repository methods
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setAdmin(resultSet.getBoolean(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    @Override
    public User save(User user) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, user.getName());
            preparedStatement.setBoolean(i++, user.isAdmin());
            preparedStatement.setString(i++, user.getEmail());
            preparedStatement.setString(i, user.getPassword());

            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    user.setUserId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User user) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            int counter = 1;
            preparedStatement.setString(counter++, user.getName());
            preparedStatement.setBoolean(counter++, user.isAdmin());
            preparedStatement.setString(counter++, user.getEmail());
            preparedStatement.setString(counter++, user.getPassword());
            preparedStatement.setLong(counter, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, user.getUserId());
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

    private User createEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setAdmin(resultSet.getBoolean("status"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("pass"));
        return user;
    }
}
