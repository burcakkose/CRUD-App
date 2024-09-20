package com.challenge.burcakkocak.exception;


import java.util.Date;

public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
    private Date timestamp;

    public ErrorDetails(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
        this.timestamp = new Date();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
