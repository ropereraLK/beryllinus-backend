package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.RoomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Integer> {

    List<RoomClass> findAllByIsActive(boolean isActive);
}
