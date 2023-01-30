package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepos extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userid);

//    @Query(value = "select u.user_id as id, count(*) from users u, bookings b, rooms r " +
//            "where u.user_id = b.user_id and b.room_number = r.room_number " +
//            "and b.floor = r.floor and r.room_type = :roomType " +
//            "group by u.user_id " +
//            "order by count(*) " +
//            "limit 3",
//            nativeQuery = true)
    @Query(value = "select u.id, u.email, u.encrypt_password, u.first_name, " +
            "u.identification_id, u.last_name, u.nationality, u.register_date, " +
            "u.user_id " +
            "from users u join bookings b on u.user_id = b.user_id " +
            "join rooms r on b.room_number = r.room_number " +
            "and b.floor = r.floor " +
            "where r.room_type = :roomType",
            nativeQuery = true)
    List<UserEntity> findTopUsers(String roomType);

    @Query(value = "select * from users",
            nativeQuery = true)
    Page<UserEntity> findAll(Pageable pageable);

    @Query(value = "select * from users u order by u.first_name, u.register_date desc",
            nativeQuery = true)
    List<UserEntity> findAllSorted();

    @Query(value = "select * from users", nativeQuery = true)
    Page<UserEntity> findAllSortedExtend(Pageable pageable);
}
