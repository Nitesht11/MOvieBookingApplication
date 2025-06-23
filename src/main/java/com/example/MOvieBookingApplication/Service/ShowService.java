package com.example.MOvieBookingApplication.Service;

import com.example.MOvieBookingApplication.DTO.ShowDTO;
import com.example.MOvieBookingApplication.Entity.Booking;
import com.example.MOvieBookingApplication.Entity.Movie;
import com.example.MOvieBookingApplication.Entity.Show;
import com.example.MOvieBookingApplication.Entity.Theater;
import com.example.MOvieBookingApplication.Respository.MovieRepository;
import com.example.MOvieBookingApplication.Respository.ShowRepository;
import com.example.MOvieBookingApplication.Respository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public Show createShow(ShowDTO showDTO){
        Movie movie= movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(()->new RuntimeException(" No Movie Found For Id"+showDTO.getMovieId()));
        Theater theater= theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(()->new RuntimeException(" No theater Found For Id"+showDTO.getTheaterId()));
       Show show = new Show();
       show.setShowTime(showDTO.getShowTime());
       show .setPrice(showDTO.getPrice());
       show.setMovie(movie);
       show.setTheater(theater);
       return showRepository.save(show);
    }

    public List<Show> getAllShows(){
        return showRepository.findAll();
    }

//    public List<Show> getShowByMovie(Long movieid){
//        Optional<List<Show>> showListBox= showRepository.findByMovieId(movieid);
//
//        if( showListBox.isPresent()){
//            return showListBox.get();
//        }else  throw new RuntimeException("No show Available for this movie");
//    }


    public List<Show> getShowByMovie(Long movieid){

        Optional<List<Show>> showListBox= showRepository.findByMovieId( movieid);
        if (showListBox.isPresent()){
            return showListBox.get();
        } else  throw new RuntimeException("no shows available for the movie");
    }

    public List<Show> getShowByTheater(Long theaterid){

        Optional<List<Show>> showListBox= showRepository.findByMovieId( theaterid);
        if (showListBox.isPresent()){
            return showListBox.get();
        } else  throw new RuntimeException("no shows available for the Theater");
    }

    public Show updateShow(Long id, ShowDTO showDTO){
        Show show =showRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No Show available for this show"+id));

        Movie movie= movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(()->new RuntimeException(" No Movie Found For Id"+showDTO.getMovieId()));
        Theater theater= theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(()->new RuntimeException(" No theater Found For Id"+showDTO.getTheaterId()));

        show.setShowTime(showDTO.getShowTime());
        show .setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);
        return showRepository.save(show);
    }

    public void  deleteShow(Long id){
        if (!showRepository.existsById(id)){
             throw  new RuntimeException("No Show available for the id"+ id);
        }

        List<Booking>bookings= showRepository.findById(id).get().getBookings();
        if(!bookings.isEmpty()){
            throw new RuntimeException("Can't delete show with exist bookings");
        }

        showRepository.deleteById(id);
    }
}
