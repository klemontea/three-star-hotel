package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.service.BookingService;
import com.miniproject.threestarhotel.service.RoomService;
import com.miniproject.threestarhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @GetMapping("/admin/home")
    public String home(Model model) {
        model.addAttribute("emptyRooms", roomService.getAllEmptyRooms().size());
        model.addAttribute("emptySingle", roomService.getEmptyRooms("single").size());
        model.addAttribute("emptyFamily", roomService.getEmptyRooms("Family").size());
        model.addAttribute("emptyDeluxe", roomService.getEmptyRooms("Deluxe").size());
        model.addAttribute("activeBookings", bookingService.getActiveBookings());
        model.addAttribute("bookings", bookingService.getBookings());
        model.addAttribute("sales", bookingService.monthlySales());
        model.addAttribute("users", userService.getUsers());
        return "index.html";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login.html";
    }
}
