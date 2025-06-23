package com.example.MOvieBookingApplication.Controller;

import com.example.MOvieBookingApplication.DTO.ShowDTO;
import com.example.MOvieBookingApplication.Entity.Show;
import com.example.MOvieBookingApplication.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/createShow")
    public ResponseEntity <Show> createShow(@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.createShow(showDTO));
    }
    @GetMapping("/getAllShows")
    public  ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }
    @GetMapping("/getShowByMovie/{id}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long id){
        return  ResponseEntity.ok(showService.getShowByMovie(id));
    }

    @GetMapping("/getShowsByTheater/{id}")
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable Long id){
         return  ResponseEntity.ok(showService.getShowByTheater(id));
    }

    @PutMapping("/ updateShow/{id}")
    public ResponseEntity<Show> updateShow( @PathVariable Long id, @RequestBody  ShowDTO showDTO){
         return ResponseEntity.ok(showService.updateShow(id,showDTO));
    }
    @DeleteMapping("/deleteShow/{id}")
    public ResponseEntity<Void> deleteShow( @PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }
}
