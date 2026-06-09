package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomSettingRepository extends   JpaRepository<RoomSetting, Integer> {
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

}
