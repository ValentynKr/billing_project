package com.epam.billing.entity;

public class UserRequest {

    private int userRequestId;
    private int userId;
    private String requestType;
    private String requestStatus; // <----- here must be enum
    private int activityId;
    private String newActivityName;
    private String comment;

    public int getUserRequestId() {
        return userRequestId;
    }

    public UserRequest setUserRequestId(int userRequestId) {
        this.userRequestId = userRequestId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public UserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public UserRequest setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public UserRequest setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public int getActivityId() {
        return activityId;
    }

    public UserRequest setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public String getNewActivityName() {
        return newActivityName;
    }

    public UserRequest setNewActivityName(String newActivityName) {
        this.newActivityName = newActivityName;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public UserRequest setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
