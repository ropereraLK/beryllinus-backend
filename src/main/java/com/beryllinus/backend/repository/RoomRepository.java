package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByIsActive(boolean isActive);


}
