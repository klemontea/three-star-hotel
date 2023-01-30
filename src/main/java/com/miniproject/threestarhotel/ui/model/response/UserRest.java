package com.miniproject.threestarhotel.ui.model.response;

import java.time.LocalDate;
import java.util.List;

public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String identificationId;
    private LocalDate registerDate;
    private String nationality;
    private List<BookingRest> bookings;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<BookingRest> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingRest> bookings) {
        this.bookings = bookings;
    }
}
