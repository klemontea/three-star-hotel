package com.miniproject.threestarhotel.ui.model.request;

import java.time.LocalDate;

public class BookingRequest {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate checkedIn;
    private Integer lengthOfStays;
    private String roomType;
    private short preferredFloor = 0;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(LocalDate checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Integer getLengthOfStays() {
        return lengthOfStays;
    }

    public void setLengthOfStays(Integer lengthOfStays) {
        this.lengthOfStays = lengthOfStays;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public short getPreferredFloor() {
        return preferredFloor;
    }

    public void setPreferredFloor(short preferredFloor) {
        this.preferredFloor = preferredFloor;
    }
}
