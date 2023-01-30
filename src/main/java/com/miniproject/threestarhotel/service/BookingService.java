package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.BookingDto;
import com.miniproject.threestarhotel.dto.MonthlySales;
import com.miniproject.threestarhotel.ui.model.request.BookingRequest;

import java.time.LocalDate;
import java.util.List;


public interface BookingService {

    BookingDto createBooking(BookingRequest user);
    double getSalesBetweenDates(LocalDate from, LocalDate to);
    List<BookingDto> getBookings();
    List<BookingDto> getAllByUserId(String userId);
    List<BookingDto> getBookingsBetweenDates(LocalDate from, LocalDate to);
    BookingDto getBookingById(String bookingId);
    void extendStays(String bookingId, short nights);
    BookingDto getBookingByRoomNumberAndFloor(int roomNumber, short floor);
    double getBillInvoice(String userId);
    List<BookingDto> getActiveBookings();
    List<MonthlySales> monthlySales();
}
