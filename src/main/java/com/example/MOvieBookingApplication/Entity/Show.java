package com.example.MOvieBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime showTime;
    private Double price;


    @ManyToOne (fetch= FetchType.EAGER) // whn we load show we want movie to load thus//
    @JoinColumn (name= "movie_id", nullable = false)
     private Movie movie;

    @ManyToOne (fetch= FetchType.EAGER) // whn we load show we want movie to load thus//
    @JoinColumn (name= "theater_id", nullable = false)
    private  Theater theater;


    @OneToMany (mappedBy = "show", fetch = FetchType.LAZY)
     private List<Booking> bookings;
}
