package com.derpate.bankapp.exception;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Date;

public class ErrorResponse {

    private int statusCode;
    private String message;
    private String path;
    private Timestamp timestamp;

    public ErrorResponse(int statusCode, String message,
                         String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
        this.timestamp = new Timestamp(new Date().getTime());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
