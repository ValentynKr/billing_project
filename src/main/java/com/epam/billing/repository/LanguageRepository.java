package com.epam.billing.repository;

import com.epam.billing.entity.Language;
import com.epam.billing.exeption.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanguageRepository extends AbstractRepository<Language> {

    private static final String SELECT_ALL = "SELECT * FROM lang_table";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM lang_table WHERE id = ?";
    private static final String EXIST_BY_ID = "SELECT * FROM lang_table WHERE EXISTS(SELECT * FROM lang_table WHERE id = ?)";
    private static final String INSERT = "INSERT INTO lang_table VALUES (DEFAULT, ?, ?)";
    private static final String DELETE = "DELETE FROM lang_table WHERE id = ?";
    private static final String UPDATE = "UPDATE lang_table SET short_name=?, full_name=? WHERE id=?";
    private static final String SELECT_BY_SHORT_NAME = "SELECT * FROM lang_table WHERE short_name = ?";

    @Override
    public List<Language> getAll() {
        List<Language> languageList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Language language = createEntity(resultSet);
                languageList.add(language);
            }
        } catch (SQLException throwables) {
            throw new DBException();
        }
        return languageList;
    }

    @Override
    public Language getById(int id) {
        Language language = new Language();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                language.setId(resultSet.getInt(1));
                language.setShortName(resultSet.getString(2));
                language.setFullName(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return language;
    }

    @Override
    public Language save(Language language) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setInt(i++, language.getId());
            preparedStatement.setString(i++, language.getShortName());
            preparedStatement.setString(i, language.getFullName());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    language.setId(id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return language;
    }

    @Override
    public Language update(Language language) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int counter = 1;
            preparedStatement.setInt(counter++, language.getId());
            preparedStatement.setString(counter++, language.getShortName());
            preparedStatement.setString(counter, language.getFullName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return language;
    }

    @Override
    public boolean delete(Language language) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, language.getId());
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

    public Language getByShortName(String shortName) {
        Language language = new Language();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_SHORT_NAME)) {
            preparedStatement.setString(1, shortName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                language.setId(resultSet.getInt(1));
                language.setShortName(resultSet.getString(2));
                language.setFullName(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throw new DBException();
        }
        return language;
    }

    private Language createEntity(ResultSet resultSet) throws SQLException {
        Language language = new Language();
        language.setId(resultSet.getInt("id"));
        language.setShortName(resultSet.getString("short_name"));
        language.setFullName(resultSet.getString("full_name"));
        return language;
    }
}
