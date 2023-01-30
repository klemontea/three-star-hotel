package com.miniproject.threestarhotel.dto;

public class MonthlySales {

    private String year;
    private String month;
    private String date;
    private String yearMonth;
    private double sales;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYearMonth() {
        return this.year + "-" + this.month;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
}
