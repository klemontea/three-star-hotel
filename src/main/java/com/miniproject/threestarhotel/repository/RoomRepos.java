package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.dto.RoomDto;
import com.miniproject.threestarhotel.entity.RoomEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepos extends CrudRepository<RoomEntity, Integer> {

    @Query(value = "select * from rooms r where r.is_available = true and " +
            "r.floor = :floor",
            nativeQuery = true)
    List<RoomEntity> findEmptyRoomsByFloor(short floor);

    @Query(value = "select * from rooms r where r.is_available = true and " +
            "r.room_type = :type",
            nativeQuery = true)
    List<RoomEntity> findEmptyRoomsByType(String type);

    @Query(value = "select * from rooms b where b.room_number = :roomNumber " +
            "and b.floor = :floor",
            nativeQuery = true)
    RoomEntity findByRoomNumber(int roomNumber, short floor);

    @Query(value = "select * from rooms r where r.is_available = true",
            nativeQuery = true)
    List<RoomEntity> findAllEmptyRooms();

    List<RoomDto> findByRoomTypeAndIsAvailableTrue(String roomType);
}
