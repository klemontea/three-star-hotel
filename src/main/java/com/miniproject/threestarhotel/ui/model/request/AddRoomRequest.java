package com.miniproject.threestarhotel.ui.model.request;

public class AddRoomRequest {

    private Integer roomNumber;
    private Short floor;
    private String roomType;
    private Double length;
    private Double width;
    private Double price;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Short getFloor() {
        return floor;
    }

    public String getRoomType() {
        return roomType;
    }

    public Double getLength() {
        return length;
    }

    public Double getWidth() {
        return width;
    }

    public Double getPrice() {
        return price;
    }
}
