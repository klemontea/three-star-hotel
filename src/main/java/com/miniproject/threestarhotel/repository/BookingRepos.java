package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.BookingEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepos extends CrudRepository<BookingEntity, Long> {

    @Query(value = "select * from bookings b where b.checked_in >= :from and " +
            "b.checked_in <= :to",
            nativeQuery = true)
    List<BookingEntity> findBetweenDateRanges(LocalDate from , LocalDate to);

    @Query(value = "select * from bookings b where b.booking_id = :bookingId",
            nativeQuery = true)
    BookingEntity findByBookingId(String bookingId);

    @Transactional
    @Modifying
    @Query(value = "update bookings b set b.checked_out = :newCheckOut, " +
            "b.total_price = :newTotalPrice where b.booking_id = :bookingId " +
            "and b.is_in_contract = true",
            nativeQuery = true)
    void extendBooking(String bookingId, LocalDate newCheckOut,
                                double newTotalPrice);

    @Query(value = "select * from bookings b where b.user_id = :userId",
            nativeQuery = true)
    List<BookingEntity> findBookingsByUserId(String userId);

    @Query(value = "select * from bookings b where b.room_number = :roomNumber " +
            "and b.is_in_contract = true and b.floor = :floor",
            nativeQuery = true)
    BookingEntity findByRoomNumberAndFloor(int roomNumber, short floor);

    @Query(value = "select * from bookings b where b.is_in_contract = true",
            nativeQuery = true)
    List<BookingEntity> findActiveBookings();
}
