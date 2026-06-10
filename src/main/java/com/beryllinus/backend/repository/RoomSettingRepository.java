package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomSetting;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomSettingRepository extends JpaRepository<RoomSetting, Integer> {
    List<RoomSetting> findByDate(
            LocalDate date
    );

    List<RoomSetting> findByDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    Optional<RoomSetting> findByRoomClassAndDate(
            RoomClass roomClass,
            LocalDate date
    );

    List<RoomSetting> findByRoomClassAndDateBetween(
            RoomClass roomClass,
            LocalDate startDate,
            LocalDate endDate
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
             select rs
             from RoomSetting rs
             where rs.roomClass.id = :roomClassId
               and rs.date >= :checkIn
               and rs.date < :checkOut
            """)
    List<RoomSetting> findForBookingWithLock(
            @Param("roomClassId") Integer roomClassId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);

}
