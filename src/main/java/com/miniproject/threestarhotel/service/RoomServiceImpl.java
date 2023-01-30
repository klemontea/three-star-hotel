package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.RoomDto;
import com.miniproject.threestarhotel.entity.RoomEntity;
import com.miniproject.threestarhotel.repository.RoomRepos;
import com.miniproject.threestarhotel.ui.model.request.AddRoomRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepos roomRepos;

    @Override
    public RoomDto createRoom(AddRoomRequest request) {

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomNumber(request.getRoomNumber());
        roomEntity.setFloor(request.getFloor());
        roomEntity.setRoomType(request.getRoomType());
        roomEntity.setLength(request.getLength());
        roomEntity.setWidth(request.getWidth());
        roomEntity.setPrice(request.getPrice());

        RoomDto result = new RoomDto();
        RoomEntity stored = roomRepos.save(roomEntity);
        BeanUtils.copyProperties(stored, result);

        return result;
    }

    @Override
    public List<RoomDto> getAllEmptyRooms() {

        List<RoomDto> result = new ArrayList<>();
        List<RoomEntity> roomEntities = roomRepos.findAllEmptyRooms();

        for (RoomEntity room : roomEntities) {

            RoomDto roomDto = new RoomDto();
            BeanUtils.copyProperties(room, roomDto);
            result.add(roomDto);
        }

        return result;
    }

    @Override
    public List<RoomDto> getEmptyRooms(short floor) {

        List<RoomDto> result = new ArrayList<>();
        List<RoomEntity> roomEntities = roomRepos.findEmptyRoomsByFloor(floor);

        for (RoomEntity room : roomEntities) {

            RoomDto roomDto = new RoomDto();
            BeanUtils.copyProperties(room, roomDto);
            result.add(roomDto);
        }

        return result;
    }

    @Override
    public List<RoomDto> getEmptyRooms(String type) {

        List<RoomDto> result = new ArrayList<>();
        List<RoomEntity> roomEntities = roomRepos.findEmptyRoomsByType(type);

        for (RoomEntity room : roomEntities) {

            RoomDto roomDto = new RoomDto();
            BeanUtils.copyProperties(room, roomDto);
            result.add(roomDto);
        }

        return result;
    }

    @Override
    public RoomDto getRoomByNumber(int roomNo, short floor) {

        RoomDto result = new RoomDto();
        RoomEntity roomEntity = roomRepos.findByRoomNumber(roomNo, floor);
        BeanUtils.copyProperties(roomEntity, result);

        return result;
    }
}
