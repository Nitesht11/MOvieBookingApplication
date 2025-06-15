package com.example.MOvieBookingApplication.Service;

import com.example.MOvieBookingApplication.DTO.TheaterDTO;
import com.example.MOvieBookingApplication.Entity.Theater;
import com.example.MOvieBookingApplication.Respository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    @Autowired
    TheaterRepository theaterRepository;

    public Theater addTheater (TheaterDTO theaterDTO){
        Theater theater  =  new Theater();

        theater.setTheaterName(theaterDTO.getTheaterName());
        theater.setTheaterLocation(theaterDTO.getTheaterLocation());
        theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
        theater.setTheaterScreenType(theater.getTheaterScreenType());

         return  theaterRepository.save(theater);
    }

    public List<Theater> getTheaterByLocation (String location){
       Optional<List<Theater>> listOfTheaterBox = theaterRepository.findByLocation(location);
       if(listOfTheaterBox.isPresent()){
           return listOfTheaterBox.get();
       } else throw new RuntimeException("No theaters founds for this location"+ location);
    }

     public Theater  updateTheater ( Long id, TheaterDTO theaterDTO){
        Theater theater= theaterRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(" No  theater found for the id"+id));

         theater.setTheaterName(theaterDTO.getTheaterName());
         theater.setTheaterLocation(theaterDTO.getTheaterLocation());
         theater.setTheaterCapacity(theaterDTO.getTheaterCapacity());
         theater.setTheaterScreenType(theater.getTheaterScreenType());


         return  theaterRepository.save(theater);

     }
     public void deleteTheater( Long id ){
         theaterRepository.deleteById(id);
     }



}
