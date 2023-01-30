package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.dto.BookingDto;
import com.miniproject.threestarhotel.dto.RoomDto;
import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.entity.OtpEntity;
import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.exception.ApplicationException;
import com.miniproject.threestarhotel.repository.OtpRepos;
import com.miniproject.threestarhotel.repository.UserRepos;
import com.miniproject.threestarhotel.service.BookingService;
import com.miniproject.threestarhotel.service.RoomService;
import com.miniproject.threestarhotel.service.UserService;
import com.miniproject.threestarhotel.ui.model.request.BookingRequest;
import com.miniproject.threestarhotel.ui.model.request.ExtendStay;
import com.miniproject.threestarhotel.ui.model.response.BookingRest;
import com.miniproject.threestarhotel.ui.model.response.ErrorMessages;
import com.miniproject.threestarhotel.ui.model.response.InvoiceBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepos userRepos;

    @Autowired
    BookingService bookingService;

    @Autowired
    RoomService roomService;

    @Autowired
    OtpRepos otpRepos;


    @PostMapping(
            path = "/users/booking",
            consumes = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public BookingRest booking(@RequestBody BookingRequest details) {   // Soal no 2    // DONE
        System.out.println("@@@@@@@@@");
        if (details.getFirstName().isEmpty() || details.getLastName().isEmpty()
                || details.getEmail().isEmpty() || details.getCheckedIn() == null
                || details.getLengthOfStays() == null || details.getRoomType().isEmpty()) {

            throw new ApplicationException(ErrorMessages
                    .MISSING_REQUIRED_FIELD
                    .getErrorMessage());
        }

        BookingDto createBooking = bookingService.createBooking(details);
        UserEntity userEntity = userRepos.findByEmail(details.getEmail());

        if (userEntity == null) throw new ApplicationException(
                ErrorMessages.USER_NOT_FOUND.getErrorMessage());
        RoomDto roomDto = roomService.getRoomByNumber(createBooking.getRoomNumber(),
                createBooking.getPreferredFloor());

        BookingRest result = new BookingRest();
        result.setBookingId(createBooking.getBookingId());
        result.setFirstName(userEntity.getFirstName());
        result.setLastName(userEntity.getLastName());
        result.setCheckedIn(createBooking.getCheckedIn());
        result.setCheckedOut(createBooking.getCheckedOut());
        result.setRoomNumber(createBooking.getRoomNumber());
        result.setRoomType(roomDto.getRoomType());
        result.setTotalPrice(createBooking.getTotalPrice());

        return result;
    }

    @GetMapping(
            path = "/sales/date/{date}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public String getSalesByDate() {
        return "sales by date";
    }

    @GetMapping(
            path = "/sales/date/{from}/{to}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public double getSalesByDateRange(@PathVariable String from
            , @PathVariable String to) {   // Soal nomor 6  // DONE

        double result = 0.00;
        LocalDate newFrom = LocalDate.parse(from);
        LocalDate newTo = LocalDate.parse(to);

        List<BookingDto> bookingDtos = bookingService.
                getBookingsBetweenDates(newFrom, newTo);

        for (BookingDto booking : bookingDtos) {
            result += booking.getTotalPrice();
        }

        return result;
    }

    @PostMapping(
            path = "/bookings/extend",
            consumes = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public void extendStays(@RequestBody ExtendStay request) {   // Soal nomor 8     // DONE

        bookingService.extendStays(request.getBookingId(),
                request.getAdditionalNight());

        var result = new BookingRest();
    }

    @GetMapping(
            path= "/bills",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<InvoiceBill> getBills(@RequestHeader("otp") String otpCode,
                                                @RequestHeader("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
        Optional<OtpEntity> userOtp = otpRepos.findOtpByEmail(userDto.getEmail());

        if (userOtp != null) {
            OtpEntity otp = userOtp.get();

            if (otp.getCode().equals(otpCode)) {

                InvoiceBill result = new InvoiceBill();
                result.setFirstName(userDto.getFirstName());
                result.setLastName(userDto.getLastName());
                result.setNationality(userDto.getNationality());
                result.setTotal(bookingService.getBillInvoice(userId));

                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(
            path= "/jwt/bills/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<InvoiceBill> getBills(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        if (userDto != null) {

            InvoiceBill result = new InvoiceBill();
            result.setFirstName(userDto.getFirstName());
            result.setLastName(userDto.getLastName());
            result.setNationality(userDto.getNationality());
            result.setTotal(bookingService.getBillInvoice(userId));

            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
