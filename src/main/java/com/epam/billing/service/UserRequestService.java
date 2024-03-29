package com.epam.billing.service;

import com.epam.billing.dto.DateStatusTypeUserRequestDTO;
import com.epam.billing.entity.Activity;
import com.epam.billing.entity.UserRequest;
import com.epam.billing.repository.ActivityRepository;
import com.epam.billing.repository.UserRequestRepository;

import java.util.List;

public class UserRequestService {

    private final UserRequestRepository userRequestRepository;

    public UserRequestService(UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    public List<UserRequest> getAll() {
        return userRequestRepository.getAll();
    }

    public boolean areUnresolvedEditRequestsPresent(int userId, int activityId) {
        return userRequestRepository.areUnresolvedEditRequestsPresent(userId, activityId);
    }

    public List<DateStatusTypeUserRequestDTO> getAllWithUserNames() {
        return userRequestRepository.getAllWithUserNames();
    }

    public List<DateStatusTypeUserRequestDTO> getAllWithUserNamesToDemonstrate(int offset, int noOfRecords) {
        return userRequestRepository.getAllWithUserNamesToDemonstrate(offset, noOfRecords);
    }

    public int getNoOfRecords() {
        return userRequestRepository.getNoOfRecords();
    }

    public void save(UserRequest userRequest) {
        userRequestRepository.save(userRequest);
    }

    public boolean delete(UserRequest userRequest) {
        return userRequestRepository.delete(userRequest);
    }

    public boolean existById(int id) {
        return userRequestRepository.existById(id);
    }

    public UserRequest getById(int id) {
        return userRequestRepository.getById(id);
    }

    public UserRequest update(UserRequest userRequest) {
        return userRequestRepository.update(userRequest);
    }

}
