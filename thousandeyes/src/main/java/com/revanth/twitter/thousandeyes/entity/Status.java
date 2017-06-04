package com.revanth.twitter.thousandeyes.entity;

/**
 * Class to send the status after a request.
 * Created by Revanth on 6/2/2017.
 */
public class Status {

    private String status;
    private String message;

    public Status() {
    }

    public Status(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
