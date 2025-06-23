package com.example.MOvieBookingApplication.Controller;

import com.example.MOvieBookingApplication.DTO.BookingDTO;
import com.example.MOvieBookingApplication.Entity.Booking;
import com.example.MOvieBookingApplication.Entity.BookingStatus;
import com.example.MOvieBookingApplication.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO){
         return ResponseEntity.ok(bookingService.createbooking(bookingDTO));
    }


    @GetMapping("/getUserbookings/({id}")
    public ResponseEntity<List<Booking>>  getUserBookings(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getUserBookings(id));
    }

    @GetMapping("/getShowBookings/({id}")
    public ResponseEntity<List<Booking>>  getShowBookings(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getShowBookings(id));
    }

    @PutMapping("{id}/confirmBooking")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long id ){
        return  ResponseEntity.ok(bookingService.confirmBooking(id));
    }


    @PutMapping("{id}/cancelBooking")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id ){
        return  ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/getBookingByStatus")
    public  ResponseEntity<List<Booking>> getBookingStatus(@PathVariable BookingStatus bookingStatus){
        return ResponseEntity.ok(bookingService.getBookingByStatus(bookingStatus));
    }

 }
