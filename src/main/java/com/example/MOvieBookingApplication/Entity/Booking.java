package com.example.MOvieBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Booking {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private Double price;
    private BookingStatus bookingStatus;


    @ElementCollection(fetch =FetchType.EAGER)
    @CollectionTable(name="Booking_Seat_Number")
    private List<String> seatNumbers;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name= "user_id",nullable = false )
    private User user ;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name= "show_id",nullable = false )
    private Show show ;

}
