package com.example.MOvieBookingApplication.Controller;

import com.example.MOvieBookingApplication.DTO.TheaterDTO;
import com.example.MOvieBookingApplication.Entity.Theater;
import com.example.MOvieBookingApplication.Service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/theater")
public class TheaterController {
     @Autowired
    private TheaterService theaterService;
     @PostMapping ("/addTheater")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<Theater> addTheatre( @RequestBody TheaterDTO theaterDTO){
         return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
     }
     @GetMapping("/getTheaterByLoaction")
     public ResponseEntity<List<Theater>> getTheaterByLocation(@RequestParam String location){
         return   ResponseEntity.ok(theaterService.getTheaterByLocation(location));
     }
     @PutMapping("/updateTheater{id}")
    @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity <Theater> updateTheater( @RequestParam Long id, @PathVariable TheaterDTO theaterDTO){
         return ResponseEntity.ok(theaterService.updateTheater(id, theaterDTO));
     }

    @DeleteMapping("/deleteTheater{id}")
    @PreAuthorize("hasRole('ADMIN')")
     public  ResponseEntity <Void> deleteTheater (@PathVariable Long id){
          theaterService.deleteTheater(id);
       return  ResponseEntity.ok().build();

     }


}
