package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.dto.BookingDto;
import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.service.*;
import com.miniproject.threestarhotel.ui.model.response.BookingRest;
import com.miniproject.threestarhotel.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;

    @Autowired
    RoomService roomService;

    @Autowired
    EmailService emailService;


//    @PostMapping(
//            path = "/register",
//            consumes = {MediaType.APPLICATION_XML_VALUE,
//                        MediaType.APPLICATION_JSON_VALUE},
//            produces = {MediaType.APPLICATION_XML_VALUE,
//                        MediaType.APPLICATION_JSON_VALUE}
//    )
//    public RegistrationResponse registration(@RequestBody RegistrationRequest details)
//            throws Exception {  // Soal nomor 1     // DONE
//
//        RegistrationResponse result = new RegistrationResponse();
//
//        if (details.getFirstName().isEmpty()
//                || details.getLastName().isEmpty()
//                || details.getIdentificationId().isEmpty()
//                || details.getEmail().isEmpty()
//                || details.getPassword().isEmpty()) {
//
//            throw new ApplicationException(ErrorMessages
//                    .MISSING_REQUIRED_FIELD
//                    .getErrorMessage());
//        }
//
//        if (userService.getUserByEmail(details.getEmail()) != null) {
//
//            throw new ApplicationException(ErrorMessages
//                    .EMAIL_ALREADY_EXISTS
//                    .getErrorMessage());
//        }
//
//        UserDto userDto = new UserDto();
//        String encrypt = bCryptPasswordEncoder.encode(details.getPassword());
//
//        BeanUtils.copyProperties(details, userDto);
//        userDto.setPassword(encrypt);
//
//        UserDto createUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createUser, result);
//
//        // Send to email
////        emailService.sendSimpleMessage();
//
//        return result;
//    }

    @GetMapping(
            path = "/users",
            produces = {MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE}
    )
    public List<UserRest> getAllUsers() {   // Soal no 3    // DONE

        List<UserRest> result = new ArrayList<>();
        List<UserDto> users = userService.getUsers();

        for (UserDto user : users) {

            List<BookingRest> bookingRests = new ArrayList<>();

            UserRest userRest = new UserRest();

            BeanUtils.copyProperties(user, userRest);

            List<BookingDto> bookingRecords = bookingService.
                    getAllByUserId(user.getUserId());

            for (BookingDto booking : bookingRecords) {

                BookingRest bookingRest = new BookingRest();
                bookingRest.setBookingId(booking.getBookingId());
                bookingRest.setFirstName(userService.getUserByUserId
                        (booking.getUserId()).getFirstName());
                bookingRest.setLastName(userService.getUserByUserId
                        (booking.getUserId()).getLastName());
                bookingRest.setCheckedIn(booking.getCheckedIn());
                bookingRest.setCheckedOut(booking.getCheckedOut());
                bookingRest.setRoomNumber(booking.getRoomNumber());
                bookingRest.setRoomType(roomService.getRoomByNumber
                        (booking.getRoomNumber(), booking.getPreferredFloor())
                        .getRoomType());
                bookingRest.setTotalPrice(booking.getTotalPrice());

                bookingRests.add(bookingRest);
            }

            userRest.setBookings(bookingRests);
            result.add(userRest);
        }

        return result;
    }

    @GetMapping(
            path = "/users/sorted",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public List<UserRest> getAllUsersSorted() {

        List<UserRest> result = new ArrayList<>();
        List<UserDto> users = userService.getUsersSorted();

        for (UserDto user : users) {

            List<BookingRest> bookingRests = new ArrayList<>();
            UserRest userRest = new UserRest();

            BeanUtils.copyProperties(user, userRest);
            result.add(userRest);
        }

        return result;
    }

    @GetMapping(path = "/users/limit/sorted",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public Page<UserDto> getUsersSorted(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order) {

        Pageable pageable = PageRequest.of(page, limit,
                (order.equals("desc")) ? Sort.by(sortBy).descending() :
                        Sort.by(sortBy).ascending());

        Page<UserDto> users = userService.getUsersPageSorted(pageable);

        return users;
    }

    @GetMapping(
            path = "/users/export",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<UserDto> listUsers = userService.getUsers();

        ExcelExporter excelExporter = new ExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @GetMapping(path = "/users/limit",
            produces = {MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE})
    public Page<UserDto> getUsers(@RequestParam(value = "page",
            defaultValue = "0") int page, @RequestParam(value = "limit",
            defaultValue = "5") int limit) {

        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDto> users = userService.getUsersPage(pageable);

        return users;
    }

    @GetMapping(
            path = "/users/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest getUserByUserId(@PathVariable String userId) {      // DONE

        UserRest result = new UserRest();
        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, result);

        List<BookingRest> bookingRests = new ArrayList<>();
        List<BookingDto> bookings = bookingService.getAllByUserId(userId);

        for (BookingDto booking : bookings) {

            BookingRest bookingRest = new BookingRest();
            bookingRest.setBookingId(booking.getBookingId());
            bookingRest.setFirstName(userService.getUserByUserId
                    (booking.getUserId()).getFirstName());
            bookingRest.setLastName(userService.getUserByUserId
                    (booking.getUserId()).getLastName());
            bookingRest.setCheckedIn(booking.getCheckedIn());
            bookingRest.setCheckedOut(booking.getCheckedOut());
            bookingRest.setRoomNumber(booking.getRoomNumber());
            bookingRest.setRoomType(roomService.getRoomByNumber
                            (booking.getRoomNumber(), booking.getPreferredFloor())
                    .getRoomType());
            bookingRest.setTotalPrice(booking.getTotalPrice());

            bookingRests.add(bookingRest);
        }

        result.setBookings(bookingRests);
        return result;
    }

    @GetMapping(
            path = "/users/floors/{floor}/rooms/{roomNumber}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest getUsersByRoomNumber(@PathVariable short floor
            , @PathVariable int roomNumber) {    //  Soal nomor 4   // DONE

        BookingDto bookingDto = bookingService.getBookingByRoomNumberAndFloor
                (roomNumber, floor);
        String userId = bookingDto.getUserId();

        return getUserByUserId(userId);
    }

    @GetMapping(
            path = "/users/top/roomtype/{type}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public List<UserRest> getTopUserByRoomType(@PathVariable String type) {     // Soal nomor 7     // DONE

        var result = new ArrayList<UserRest>();
        List<UserDto> topUsers = userService.getTopUsers(type);

        for (UserDto topUser : topUsers) {

            var userRest = new UserRest();
            BeanUtils.copyProperties(topUser, userRest);
            result.add(userRest);
        }

        return result;
    }

}
