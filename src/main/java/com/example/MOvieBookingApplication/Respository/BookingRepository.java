package com.example.MOvieBookingApplication.Respository;

import com.example.MOvieBookingApplication.Entity.Booking;
import com.example.MOvieBookingApplication.Entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userid);

    List<Booking> findByShowId(Long showid);


    List <Booking> findByBookingStatus( BookingStatus bookingStatus);

}
