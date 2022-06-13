package com.epam.billing.dto;

import com.epam.billing.entity.RequestStatus;
import com.epam.billing.entity.RequestType;

import java.sql.Timestamp;

public class DateStatusTypeUserRequestDTO {
    private int requestId;
    private Timestamp timestamp;
    private RequestStatus requestStatus;
    private RequestType requestType;
    private String userName;

    @Override
    public String toString() {
        return "DateStatusTypeUserRequestDTO{" +
                "timestamp=" + timestamp +
                ", requestStatus=" + requestStatus +
                ", requestType=" + requestType +
                ", userName='" + userName + '\'' +
                '}';
    }

    public int getRequestId() {
        return requestId;
    }

    public DateStatusTypeUserRequestDTO setRequestId(int requestId) {
        this.requestId = requestId;
        return this;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public DateStatusTypeUserRequestDTO setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public DateStatusTypeUserRequestDTO setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public DateStatusTypeUserRequestDTO setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public DateStatusTypeUserRequestDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
