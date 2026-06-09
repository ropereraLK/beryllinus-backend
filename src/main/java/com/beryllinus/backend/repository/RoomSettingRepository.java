package com.beryllinus.backend.repository;

import com.beryllinus.backend.model.room.RoomSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSettingRepository extends   JpaRepository<RoomSetting, Integer> {

}
