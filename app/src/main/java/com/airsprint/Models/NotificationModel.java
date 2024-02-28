package com.airsprint.Models;

public class NotificationModel {
    private String bookingId;
    private String notificationMessage;
    private String time;
    private String date;
    private int status;

    public NotificationModel(String bookingId, String notificationMessage, String time, String date,  int status) {
        this.bookingId = bookingId;
        this.notificationMessage = notificationMessage;
        this.time = time;
        this.date = date;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
