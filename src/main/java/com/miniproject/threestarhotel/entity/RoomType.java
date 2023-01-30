package com.miniproject.threestarhotel.entity;

public enum RoomType {
    SINGLE, FAMILY, DELUXE;

    private String option;

    RoomType() {
    }

    private RoomType(String s) {

        this.option = s;
    }

    public String getOption() {
        return option;
    }
}
