package com.bhuvanesh.talenthive.exception;


public class THException extends Exception {

    private int statusCode;
    private String message;

    public THException(int statusCode) {
        this(statusCode, "Something wrong!!!.Please try after sometime.");
    }

    public THException(String message) {
        this(-1, message);
    }

    public THException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message != null ? message : this.message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
