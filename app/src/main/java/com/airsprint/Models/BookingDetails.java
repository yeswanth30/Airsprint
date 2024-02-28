package com.airsprint.Models;

public class BookingDetails {
    private String senderName;
    private String senderMobile;
    private String senderMessage;
    private String receiverName;
    private String receiverMobile;
    private String receiverMessage;
    private String packageType;

    public BookingDetails() {
        // Default constructor required for Firebase
    }

    public BookingDetails(String senderName, String senderMobile, String senderMessage,
                          String receiverName, String receiverMobile, String receiverMessage,
                          String packageType) {
        this.senderName = senderName;
        this.senderMobile = senderMobile;
        this.senderMessage = senderMessage;
        this.receiverName = receiverName;
        this.receiverMobile = receiverMobile;
        this.receiverMessage = receiverMessage;
        this.packageType = packageType;
    }

    // Getters and setters
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
