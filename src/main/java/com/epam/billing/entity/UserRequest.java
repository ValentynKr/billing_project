package com.epam.billing.entity;

public class UserRequest {

    private int userRequestId;
    private int userId;
    private RequestType requestType;
    private RequestStatus requestStatus;
    private int activityId;
    private String newActivityName;
    private String comment;

    @Override
    public String toString() {
        return "UserRequest{" +
                "userRequestId=" + userRequestId +
                ", userId=" + userId +
                ", requestType='" + requestType + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", activityId=" + activityId +
                ", newActivityName='" + newActivityName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

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

    public RequestType getRequestType() {
        return requestType;
    }

    public UserRequest setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public UserRequest setRequestStatus(RequestStatus requestStatus) {
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
