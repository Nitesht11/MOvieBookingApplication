package com.example.MOvieBookingApplication.Controller;

import com.example.MOvieBookingApplication.DTO.MovieDTO;
import com.example.MOvieBookingApplication.Entity.Movie;
import com.example.MOvieBookingApplication.Service.MovieService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/addMovie")

    @PreAuthorize("has Role('ADMIN')")
    public ResponseEntity <Movie> addMovie (@RequestBody MovieDTO movieDTO) {

        return  ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @GetMapping("/getAllMovies")
     public ResponseEntity <List<Movie>>getAllMovies (){
        return  ResponseEntity.ok(movieService.getAllMovies());
    }


    @GetMapping("/getMovieByGenre")
    public ResponseEntity<List<Movie>> getMovieByGenre( @RequestParam String genre){
         return ResponseEntity.ok(movieService.getMovieByGenre(genre));
    }

    @GetMapping("/getMoviesByLanguage")
    public ResponseEntity <List<Movie>> getMoviesByLanguage( @RequestParam String language){
        return ResponseEntity.ok(movieService.getMovieByLanguage(language));
    }
    @GetMapping("/getMoviesByTitle")
    public ResponseEntity<Movie>  getMoviesByTitle( @RequestParam String name){
        return ResponseEntity.ok(movieService.getMoviesByTitle(name));
    }

    @PutMapping("/uptateMovie")
    @PreAuthorize("has Role('ADMIN')")

    public  ResponseEntity<Movie> updateMovie (@PathVariable Long id, @RequestBody MovieDTO movieDTO){
         return  ResponseEntity.ok(movieService. updateMovie(id, movieDTO));
    }
    @DeleteMapping("/deleteMovie")
    @PreAuthorize("has Role('ADMIN')")

    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
     movieService.deleteMovie(id);    // since return type is void thus call method is written above//
     return ResponseEntity.ok().build();
    }
}

