package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.RoomDto;
import com.miniproject.threestarhotel.ui.model.request.AddRoomRequest;

import java.util.List;

public interface RoomService {

    List<RoomDto> getEmptyRooms(short floor);
    List<RoomDto> getEmptyRooms(String type);
    List<RoomDto> getAllEmptyRooms();
    RoomDto getRoomByNumber(int roomNo, short floor);
    RoomDto createRoom(AddRoomRequest request);
}
