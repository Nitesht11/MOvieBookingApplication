package com.example.MOvieBookingApplication.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)  // it means to identify it is PK
    private Long id;
    private String name;
    private String description;
    private String genre;
    private Integer duration;
    private LocalDate releaseDate;
    private String language;


    @OneToMany (mappedBy = "movie", fetch = FetchType.LAZY)
    // we dont want load the show when movie is loaded thus //

    @JsonIgnore
    private List<Show> show;
}