package com.beryllinus.backend.repository;

import com.beryllinus.backend.enumuration.RoomClassType;
import com.beryllinus.backend.model.room.RoomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Integer> {

    /**
     * Get all active RoomClasses.
     */
    List<RoomClass> findAllByIsActive(
            boolean isActive
    );

    /**
     * Get active RoomClass by id.
     */
    Optional<RoomClass> findByIdAndIsActive(
            Integer id,
            boolean isActive
    );

    /**
     * Get RoomClass by business type.
     */
    Optional<RoomClass> findByRoomClassType(
            RoomClassType roomClassType
    );
}
