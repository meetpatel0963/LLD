package com.meetpatel.bookmyshow.controller;

import com.meetpatel.bookmyshow.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MovieController {
    final private MovieService movieService;

    public String createMovie(@NonNull final String movieName) {
        return movieService.createMovie(movieName).getId();
    }

}
