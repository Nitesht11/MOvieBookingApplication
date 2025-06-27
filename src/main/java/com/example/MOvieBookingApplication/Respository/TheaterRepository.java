package com.example.MOvieBookingApplication.Respository;

import com.example.MOvieBookingApplication.Entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository <Theater,Long>
{
    Optional<List<Theater>> findByTheaterLocation(String theaterLocation);

    @Query("SELECT t FROM Theater t WHERE t.theaterLocation = :location")
    Optional<List<Theater>> findByLocation(@Param("location") String location);

}
