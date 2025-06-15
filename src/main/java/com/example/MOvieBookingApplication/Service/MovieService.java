package com.example.MOvieBookingApplication.Service;

import com.example.MOvieBookingApplication.DTO.MovieDTO;
import com.example.MOvieBookingApplication.Entity.Movie;
import com.example.MOvieBookingApplication.Respository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> getMovieByGenre(String genre) {
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByGenre(genre);

        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else throw new RuntimeException(" No movie found for genre"+ genre);

    }
    public List<Movie> getMovieByLanguage(String language) {
        Optional<List<Movie>> listOfMovieBox = movieRepository.findBylanguage(language);
        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else throw new RuntimeException(" No movie found for langauge" + language);
    }
     public Movie getMoviesByTitle(String name) {
        Optional<Movie> movieBox = movieRepository.findByName(name);

         if( movieBox.isPresent()){
              return  movieBox.get();
         } else throw new RuntimeException(" No movie found for TiTle" + name);
        }
     public Movie updateMovie(Long id,MovieDTO movieDTO){
           Movie movie  = movieRepository.findById(id)
                   .orElseThrow(()->new RuntimeException(" no Movie for the id "+id));

         movie.setName(movieDTO.getName());
         movie.setDescription(movieDTO.getDescription());
         movie.setGenre(movieDTO.getGenre());
         movie.setReleaseDate(movieDTO.getReleaseDate());
         movie.setDuration(movieDTO.getDuration());
         movie.setLanguage(movieDTO.getLanguage());
         return movieRepository.save(movie);
     }
     public void deleteMovie (Long id) {
         movieRepository.deleteById(id);
     }
}