package com.miniproject.threestarhotel.dto;

import java.time.LocalDate;

public class BookingDto {

    private long id;
    private String bookingId;
    private String userId;
    private int roomNumber;
    private LocalDate checkedIn;
    private LocalDate checkedOut;
    private int lengthOfStays;
    private short preferredFloor = 0;
    private double totalPrice;
    private boolean isInContract = false;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(LocalDate checkedIn) {
        this.checkedIn = checkedIn;
    }

    public LocalDate getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(LocalDate checkedOut) {
        this.checkedOut = checkedOut;
    }

    public int getLengthOfStays() {
        return lengthOfStays;
    }

    public void setLengthOfStays(int lengthOfStays) {
        this.lengthOfStays = lengthOfStays;
    }

    public short getPreferredFloor() {
        return preferredFloor;
    }

    public void setPreferredFloor(short preferredFloor) {
        this.preferredFloor = preferredFloor;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isInContract() {
        return isInContract;
    }

    public void setInContract(boolean inContract) {
        isInContract = inContract;
    }
}
