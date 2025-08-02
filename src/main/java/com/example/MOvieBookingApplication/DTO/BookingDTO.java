package com.example.MOvieBookingApplication.DTO;

import com.example.MOvieBookingApplication.Entity.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder

public class BookingDTO {

    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private  Double price;
    private BookingStatus bookingStatus;
    private List<String>  seatNumbers;
    private Long userId;
    private Long showId;
}
