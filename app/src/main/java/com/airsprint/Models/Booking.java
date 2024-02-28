package com.airsprint.Models;

public class Booking {
    private String source;
    private String destination;
    private String detailedAddress;
    private int height;
    private int weight;
    private int width;
    private int destinationPrice;
    private int additionalCharge;
    private int weightCharge;
    private int totalAmount;
    private String selectedDate;

    public Booking() {
        // Default constructor required for Firebase
    }

    public Booking(String source, String destination, String detailedAddress, int height, int weight, int width, int destinationPrice, int additionalCharge, int weightCharge, int totalAmount, String selectedDate) {
        this.source = source;
        this.destination = destination;
        this.detailedAddress = detailedAddress;
        this.height = height;
        this.weight = weight;
        this.width = width;
        this.destinationPrice = destinationPrice;
        this.additionalCharge = additionalCharge;
        this.weightCharge = weightCharge;
        this.totalAmount = totalAmount;
        this.selectedDate = selectedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDestinationPrice() {
        return destinationPrice;
    }

    public void setDestinationPrice(int destinationPrice) {
        this.destinationPrice = destinationPrice;
    }

    public int getAdditionalCharge() {
        return additionalCharge;
    }

    public void setAdditionalCharge(int additionalCharge) {
        this.additionalCharge = additionalCharge;
    }

    public int getWeightCharge() {
        return weightCharge;
    }

    public void setWeightCharge(int weightCharge) {
        this.weightCharge = weightCharge;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}
