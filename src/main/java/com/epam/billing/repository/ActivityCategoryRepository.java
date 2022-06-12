package com.epam.billing.repository;

import com.epam.billing.dto.ActivityCategoryIdLocalizedNameDTO;
import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.entity.ActivityCategoryStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityCategoryRepository extends AbstractRepository<ActivityCategory> {

    private static final String SELECT_ALL = "SELECT * FROM activity_category";
    private static final String SELECT_ALL_LOCALIZED = "SELECT\n" +
            "activity_category.id, activity_category_description.name\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where language_id=?;";
    private static final String SELECT_OPENED_AND_LOCALIZED = "SELECT\n" +
            "activity_category.id, activity_category_description.name, activity_category.activity_category_status\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where language_id=? AND activity_category.activity_category_status = 'OPENED';";
    private static final String SELECT_ALL_LOCALIZED_WITH_STATUS = "SELECT\n" +
            "activity_category.id, activity_category_description.name, activity_category.activity_category_status\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where language_id = ? order by activity_category_status, activity_category_description.name;";
    private static final String SELECT_ALL_WHERE_ID = "SELECT * FROM  activity_category WHERE id = ?";
    private static final String SELECT_ALL_WHERE_ID_LOCALIZED = "SELECT\n" +
            "activity_category.id, activity_category_description.name, activity_category.activity_category_status\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where id = ? and language_id = ?;";
    private static final String SELECT_ALL_WHERE_ID_WITH_DESCRIPTIONS = "SELECT\n" +
            "activity_category.id, activity_category_description.name, activity_category.activity_category_status\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where id = ?;";
    private static final String SELECT_ALL_WHERE_NAME = "SELECT\n" +
            "activity_category.id, activity_category_description.name, activity_category.activity_category_status\n" +
            "FROM  activity_category\n" +
            "inner JOIN activity_category_description\n" +
            "on activity_category_description.category_id = activity_category.id\n" +
            "where activity_category_description.name=? and language_id=?;";
    private static final String EXIST_BY_ID = "SELECT * FROM activity_category WHERE EXISTS (SELECT * " +
            "FROM activity_category WHERE id = ?)";
    private static final String INSERT = "INSERT INTO activity_category VALUES (DEFAULT, ?)";
    private static final String DELETE = "DELETE FROM activity_category WHERE id = ?";
    private static final String UPDATE = "UPDATE activity_category SET activity_category_status=? WHERE id=?";

    @Override
    public List<ActivityCategory> getAll() {
        List<ActivityCategory> activityCategoryList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategory activityCategory = new ActivityCategory();
                activityCategory.setCategoryId(resultSet.getInt(1));
                activityCategory.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(2)));
                activityCategoryList.add(activityCategory);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryList;
    }

    public List<ActivityCategoryIdLocalizedNameDTO> getAllWithLocalizedNames(int languageId) {
        List<ActivityCategoryIdLocalizedNameDTO> activityCategoryIdLocalizedNameDTOList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategoryIdLocalizedNameDTO activityCategoryIdLocalizedNameDTO = new ActivityCategoryIdLocalizedNameDTO();
                activityCategoryIdLocalizedNameDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameDTOList.add(activityCategoryIdLocalizedNameDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryIdLocalizedNameDTOList;
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getAllWithLocalizedNameStatusDTO(int languageId) {
        List<ActivityCategoryIdLocalizedNameStatusDTO> categoryIdLocalizedNameStatusList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOCALIZED_WITH_STATUS)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
                categoryIdLocalizedNameStatusList.add(activityCategoryIdLocalizedNameStatusDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categoryIdLocalizedNameStatusList;
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getOpenedWithLocalizedNames(int languageId) {
        List<ActivityCategoryIdLocalizedNameStatusDTO> activityCategoryIdLocalizedNameStatusDTOList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPENED_AND_LOCALIZED)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
                activityCategoryIdLocalizedNameStatusDTOList.add(activityCategoryIdLocalizedNameStatusDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryIdLocalizedNameStatusDTOList;
    }

    @Override
    public ActivityCategory getById(int id) {
        ActivityCategory activityCategory = new ActivityCategory();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activityCategory.setCategoryId(resultSet.getInt(1));
                activityCategory.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategory;
    }

    public ActivityCategoryIdLocalizedNameStatusDTO getByIdLocalized(int categoryId, int languageId) {
        ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID_LOCALIZED)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryIdLocalizedNameStatusDTO;
    }

    public List<ActivityCategoryIdLocalizedNameStatusDTO> getByIdWithDescriptions(int categoryId) {
        List<ActivityCategoryIdLocalizedNameStatusDTO> activityCategoryIdLocalizedNameStatusDTOList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_ID_WITH_DESCRIPTIONS)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
                activityCategoryIdLocalizedNameStatusDTOList.add(activityCategoryIdLocalizedNameStatusDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryIdLocalizedNameStatusDTOList;
    }

    public ActivityCategoryIdLocalizedNameStatusDTO getByNameNotSafe(String name, int languageId) {
        ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategoryIdLocalizedNameStatusDTO;
    }

    public Optional<ActivityCategoryIdLocalizedNameStatusDTO> getByNameSafe(String name, int languageId) {
        ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = new ActivityCategoryIdLocalizedNameStatusDTO();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WHERE_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                activityCategoryIdLocalizedNameStatusDTO.setCategoryId(resultSet.getInt(1));
                activityCategoryIdLocalizedNameStatusDTO.setCategoryName(resultSet.getString(2));
                activityCategoryIdLocalizedNameStatusDTO.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(resultSet.getString(3)));
            }} else {activityCategoryIdLocalizedNameStatusDTO = null;}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(activityCategoryIdLocalizedNameStatusDTO);
    }

    @Override
    public ActivityCategory save(ActivityCategory activityCategory) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setString(i, activityCategory.getActivityCategoryStatus().toString());
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int counter = 1;
            preparedStatement.setString(counter++, activityCategory.getActivityCategoryStatus().toString());
            preparedStatement.setInt(counter, activityCategory.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return activityCategory;
    }

    @Override
    public boolean delete(ActivityCategory activityCategory) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, activityCategory.getCategoryId());
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

}
