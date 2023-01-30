package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.dto.RoomDto;
import com.miniproject.threestarhotel.exception.ApplicationException;
import com.miniproject.threestarhotel.service.RoomService;
import com.miniproject.threestarhotel.ui.model.request.AddRoomRequest;
import com.miniproject.threestarhotel.ui.model.response.ErrorMessages;
import com.miniproject.threestarhotel.ui.model.response.RoomRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    RoomService roomService;


    @PostMapping(
            path = "/rooms/add",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public void addRoom(@RequestBody AddRoomRequest details) {

        if (details.getRoomNumber() == null || details.getFloor() == null
                || details.getRoomType().isEmpty() || details.getLength() == null
                || details.getWidth() == null || details.getPrice() == null) {

            throw new ApplicationException(ErrorMessages
                    .MISSING_REQUIRED_FIELD
                    .getErrorMessage());
        }

        roomService.createRoom(details);
    }

    @GetMapping(
            path = "/rooms/empty/floor/{level}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public List<RoomRest> getEmptyRoomsByFloor(@PathVariable short level) {  // Soal nomor 5     // DONE

        var result = new ArrayList<RoomRest>();
        List<RoomDto> roomDtos = roomService.getEmptyRooms(level);

        for (RoomDto room : roomDtos) {

            var roomRest = new RoomRest();
            BeanUtils.copyProperties(room, roomRest);
            result.add(roomRest);
        }

        return result;
    }

    @GetMapping(
            path = "/rooms/empty/type/{type}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE}
    )
    public List<RoomRest> getEmptyRoomsByType(@PathVariable String type) {   //  Soal nomor 5    //  DONE

        var result = new ArrayList<RoomRest>();
        List<RoomDto> roomDtos = roomService.getEmptyRooms(type);

        for (RoomDto room : roomDtos) {

            var roomRest = new RoomRest();
            BeanUtils.copyProperties(room, roomRest);
            result.add(roomRest);
        }

        return result;
    }

}
