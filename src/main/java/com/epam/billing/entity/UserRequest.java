package com.epam.billing.entity;

import java.sql.Timestamp;

public class UserRequest {

    private int userRequestId;
    private Timestamp timestamp;
    private int userId;
    private RequestType requestType;
    private RequestStatus requestStatus;
    private int activityCategoryId;
    private int activityId;
    private float userActivityDuration;
    private String newActivityName;
    private String comment;

    public int getActivityId() {
        return activityId;
    }

    public UserRequest setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public UserRequest setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userRequestId=" + userRequestId +
                ", date=" + timestamp +
                ", userId=" + userId +
                ", requestType=" + requestType +
                ", requestStatus=" + requestStatus +
                ", activityCategoryId=" + activityCategoryId +
                ", activityId=" + activityId +
                ", userActivityDuration=" + userActivityDuration +
                ", newActivityName='" + newActivityName + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public int getActivityCategoryId() {
        return activityCategoryId;
    }

    public UserRequest setActivityCategoryId(int activityCategoryId) {
        this.activityCategoryId = activityCategoryId;
        return this;
    }

    public float getUserActivityDuration() {
        return userActivityDuration;
    }

    public UserRequest setUserActivityDuration(float userActivityDuration) {
        this.userActivityDuration = userActivityDuration;
        return this;
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
