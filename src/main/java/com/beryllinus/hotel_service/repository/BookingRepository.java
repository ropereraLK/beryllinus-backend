package com.beryllinus.hotel_service.repository;

import com.beryllinus.hotel_service.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends MongoRepository<Booking, UUID> {


}
