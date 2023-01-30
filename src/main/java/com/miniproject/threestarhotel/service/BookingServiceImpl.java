package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.BookingDto;
import com.miniproject.threestarhotel.dto.MonthlySales;
import com.miniproject.threestarhotel.entity.BookingEntity;
import com.miniproject.threestarhotel.entity.RoomEntity;
import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.exception.ApplicationException;
import com.miniproject.threestarhotel.repository.BookingRepos;
import com.miniproject.threestarhotel.repository.RoomRepos;
import com.miniproject.threestarhotel.repository.UserRepos;
import com.miniproject.threestarhotel.security.Utils;
import com.miniproject.threestarhotel.ui.model.request.BookingRequest;
import com.miniproject.threestarhotel.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepos bookingRepos;

    @Autowired
    RoomRepos roomRepos;

    @Autowired
    UserRepos userRepos;

    @Autowired
    Utils utils;

    @Override
    public BookingDto createBooking(BookingRequest request) {

        // First, generate required resource
        BookingEntity bookingEntity = new BookingEntity();
        String generateBookingId = utils.generateUserId();
        UserEntity userEntity = userRepos.findByEmail(request.getEmail());

        if (userEntity == null) throw new ApplicationException(ErrorMessages.
                USER_NOT_FOUND.getErrorMessage());

        String extractedUserId = userEntity.getUserId();
        LocalDate checkIn = request.getCheckedIn();
        LocalDate checkOut = checkIn.plusDays(request.getLengthOfStays());

        // Check room which is available either by floor preference of not
        List<RoomEntity> rooms = (ArrayList<RoomEntity>) roomRepos.findAll();
        for (RoomEntity room : rooms) {

            if (request.getPreferredFloor() == 0) {

                if (room.isAvailable() &&
                        room.getRoomType().equals(request.getRoomType())) {

                    int roomNumber = room.getRoomNumber();
                    short floor = room.getFloor();
                    double totalPrice = room.getPrice() * request.getLengthOfStays();
                    room.setAvailable(false);

                    bookingEntity.setRoomNumber(roomNumber);    // OK
                    bookingEntity.setTotalPrice(totalPrice);    // OK
                    bookingEntity.setFloor(floor);
                    break;
                }

            } else {

                if (room.isAvailable() &&
                        room.getFloor() == request.getPreferredFloor() &&
                        room.getRoomType().equals(request.getRoomType())) {

                    int roomNumber = room.getRoomNumber();
                    short floor = room.getFloor();
                    double totalPrice = room.getPrice() * request.getLengthOfStays();
                    room.setAvailable(false);

                    bookingEntity.setRoomNumber(roomNumber);    // OK
                    bookingEntity.setTotalPrice(totalPrice);    // OK
                    bookingEntity.setFloor(floor);
                    break;
                }
            }

        }

        // Persist all data into booking entity
        bookingEntity.setBookingId(generateBookingId);  // OK
        bookingEntity.setUserId(extractedUserId);   // OK
        bookingEntity.setCheckedIn(checkIn);    // OK
        bookingEntity.setCheckedOut(checkOut);  // OK
        bookingEntity.setInContract(true);  // OK

        // Stored and return
        BookingEntity storedEntity = bookingRepos.save(bookingEntity);
        BookingDto result = new BookingDto();
        BeanUtils.copyProperties(storedEntity, result);
        result.setLengthOfStays(request.getLengthOfStays());
        result.setPreferredFloor(storedEntity.getFloor());

        return result;
    }

    @Override
    public double getSalesBetweenDates(LocalDate from, LocalDate to) {

        double result = 0.0;
        List<BookingDto> bookingDtos = getBookingsBetweenDates(from, to);

        for (BookingDto booking : bookingDtos) {
            result += booking.getTotalPrice();
        }

        return result;
    }

    @Override
    public List<BookingDto> getBookings() {

        List<BookingDto> result = new ArrayList<>();
        List<BookingEntity> bookingEntities = (List<BookingEntity>)
                bookingRepos.findAll();

        for (BookingEntity b : bookingEntities) {

            BookingDto bookingDto = new BookingDto();
            bookingDto.setId(b.getId());
            bookingDto.setBookingId(b.getBookingId());
            bookingDto.setUserId(b.getUserId());
            bookingDto.setRoomNumber(b.getRoomNumber());
            bookingDto.setPreferredFloor(b.getFloor());
            bookingDto.setCheckedIn(b.getCheckedIn());
            bookingDto.setCheckedOut(b.getCheckedOut());
            bookingDto.setLengthOfStays(Period.between(b.getCheckedIn(),
                    b.getCheckedOut()).getDays());
            bookingDto.setTotalPrice(b.getTotalPrice());
            bookingDto.setInContract(b.isInContract());

            result.add(bookingDto);
        }

        return result;
    }

    @Override
    public List<BookingDto> getActiveBookings() {

        List<BookingDto> result = new ArrayList<>();
        List<BookingEntity> bookingEntities =
                (List<BookingEntity>) bookingRepos.findActiveBookings();

        for (BookingEntity b : bookingEntities) {

            BookingDto bookingDto = new BookingDto();
            bookingDto.setId(b.getId());
            bookingDto.setBookingId(b.getBookingId());
            bookingDto.setUserId(b.getUserId());
            bookingDto.setRoomNumber(b.getRoomNumber());
            bookingDto.setPreferredFloor(b.getFloor());
            bookingDto.setCheckedIn(b.getCheckedIn());
            bookingDto.setCheckedOut(b.getCheckedOut());
            bookingDto.setLengthOfStays(Period.between(b.getCheckedIn(),
                    b.getCheckedOut()).getDays());
            bookingDto.setTotalPrice(b.getTotalPrice());
            bookingDto.setInContract(b.isInContract());

            result.add(bookingDto);
        }

        return result;
    }

    @Override
    public List<BookingDto> getAllByUserId(String userId) {

        List<BookingDto> result = new ArrayList<>();
        List<BookingEntity> bookingEntities = bookingRepos.
                findBookingsByUserId(userId);

        for (BookingEntity booking : bookingEntities) {

            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            bookingDto.setPreferredFloor(booking.getFloor());
            result.add(bookingDto);
        }

        return result;
    }

    @Override
    public BookingDto getBookingByRoomNumberAndFloor(int roomNumber, short floor) {

        var result = new BookingDto();
        BookingEntity bookingEntity = bookingRepos.findByRoomNumberAndFloor(
                roomNumber, floor);

        BeanUtils.copyProperties(bookingEntity, result);
        result.setLengthOfStays(0);
        result.setPreferredFloor((short)0);

        return result;
    }

    @Override
    public List<BookingDto> getBookingsBetweenDates(LocalDate from, LocalDate to) {

        List<BookingDto> result = new ArrayList<>();

        List<BookingEntity> bookingEntities = bookingRepos.
                findBetweenDateRanges(from, to);

        for (BookingEntity booking : bookingEntities) {

            BookingDto bookingDto = new BookingDto();
            BeanUtils.copyProperties(booking, bookingDto);
            result.add(bookingDto);
        }

        return result;
    }

    @Override
    public BookingDto getBookingById(String bookingId) {

        BookingDto result = new BookingDto();

        BookingEntity bookingEntity = bookingRepos.findByBookingId(bookingId);
        BeanUtils.copyProperties(bookingEntity, result);
        result.setPreferredFloor(bookingEntity.getFloor());

        return result;
    }

    @Override
    public void extendStays(String bookingId, short nights) {

        BookingDto result = new BookingDto();

        BookingEntity oldBooking = bookingRepos.findByBookingId(bookingId);
        short floor = oldBooking.getFloor();
        int roomNumber = oldBooking.getRoomNumber();
        double oldTotalPrice = oldBooking.getTotalPrice();
        LocalDate checkOut = oldBooking.getCheckedOut();
        LocalDate newCheckOut = checkOut.plusDays(nights);

        RoomEntity roomEntity = roomRepos.findByRoomNumber(roomNumber, floor);
        double roomPrice = roomEntity.getPrice();
        double newTotalPrice = oldTotalPrice + (roomPrice * nights);

        bookingRepos.extendBooking(bookingId, newCheckOut, newTotalPrice);
    }

    public double getBillInvoice(String userId) {

        double sum = 0;
        List<BookingEntity> bookings = bookingRepos.findBookingsByUserId(userId);

        for (BookingEntity booking : bookings) {
            sum += booking.getTotalPrice();
        }

        return sum;
    }

    public List<MonthlySales> monthlySales() {

        List<BookingEntity> bookings = (List<BookingEntity>) bookingRepos.findAll();
        Collections.sort(bookings);
        List<MonthlySales> result = new ArrayList<>();
        var monthSales = new LinkedHashMap<String, Double>();

        for (BookingEntity booking : bookings) {

            LocalDate time = booking.getCheckedIn();
            String month = String.valueOf(time.getMonthValue());
            double sales = booking.getTotalPrice();

            if (monthSales.containsKey(month)) {
                monthSales.put(month, monthSales.get(month) + sales);
            } else {
                monthSales.put(month, sales);
            }
        }

        for (Map.Entry<String, Double> entry : monthSales.entrySet()) {

            MonthlySales sales = new MonthlySales();
            sales.setMonth(String.valueOf(entry.getKey()));
            sales.setSales(entry.getValue());
            sales.setYear("2022");
            sales.setDate("01");

            result.add(sales);
        }

        return result;
    }
}
