package com.miniproject.threestarhotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RoomEmptySchedule {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RoomService roomService;


//    @Scheduled(fixedRate = 1000000, initialDelay = 1000000)
//    public void getEmptyRoomsInSchedule() throws MessagingException {
//
//        MimeMessage msg = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        List<RoomDto> roomDtos = roomService.getAllEmptyRooms();
//        String message = "";
//
//        for (RoomDto room : roomDtos) {
//            message += "Empty rooms at room number=" + room.getRoomNumber() +
//                    ", on floor "  + room.getFloor() + "<br>";
//        }
//
//        helper.setTo("handry.k.wgs@gmail.com");
//        helper.setSubject("List of Empty Rooms");
//        helper.setText(message, true);
//
//        emailSender.send(msg);
//    }
}
