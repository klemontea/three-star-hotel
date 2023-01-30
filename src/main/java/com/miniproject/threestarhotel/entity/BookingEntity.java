package com.miniproject.threestarhotel.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class BookingEntity implements Comparable<BookingEntity> {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String bookingId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private short floor;

    @Column(nullable = false)
    private LocalDate checkedIn;

    @Column(nullable = false)
    private LocalDate checkedOut;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
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

    @Override
    public int compareTo(BookingEntity bookingEntity) {
        return getCheckedIn().compareTo(bookingEntity.getCheckedIn());
    }
}
