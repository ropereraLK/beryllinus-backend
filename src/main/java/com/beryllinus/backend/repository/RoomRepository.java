package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByIsActive(boolean isActive);
    List<Room> findByRoomClass_IdAndIsActive(
            Long roomClassId,
            Boolean isActive
    );

    Optional<Room> findByIdAndIsActive(
            Integer id,
            Boolean isActive
    );


}
