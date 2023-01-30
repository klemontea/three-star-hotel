package com.miniproject.threestarhotel.ui.model.response;

import java.time.LocalDate;

public class BookingRest {

    private String bookingId;
    private String firstName;
    private String lastName;
    private LocalDate checkedIn;
    private LocalDate checkedOut;
    private int roomNumber;
    private String roomType;
    private double totalPrice;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
