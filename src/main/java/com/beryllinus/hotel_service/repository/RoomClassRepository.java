package com.beryllinus.hotel_service.repository;

import com.beryllinus.hotel_service.model.room.Room;
import com.beryllinus.hotel_service.model.room.RoomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Integer> {

    List<RoomClass> findAllByIsActive(boolean isActive);
}
