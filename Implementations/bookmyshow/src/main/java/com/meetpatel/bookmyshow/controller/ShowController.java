package com.meetpatel.bookmyshow.controller;

import com.meetpatel.bookmyshow.model.Movie;
import com.meetpatel.bookmyshow.model.Screen;
import com.meetpatel.bookmyshow.model.Seat;
import com.meetpatel.bookmyshow.model.Show;
import com.meetpatel.bookmyshow.service.MovieService;
import com.meetpatel.bookmyshow.service.SeatAvailabilityService;
import com.meetpatel.bookmyshow.service.ShowService;
import com.meetpatel.bookmyshow.service.TheatreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ShowController {
    private final SeatAvailabilityService seatAvailabilityService;
    private final ShowService showService;
    private final TheatreService theatreService;
    private final MovieService movieService;

    public String createShow(@NonNull final String movieId, @NonNull final String screenId, @NonNull final Date startTime,
                             @NonNull final Integer durationInSeconds) {
        final Screen screen = theatreService.getScreen(screenId);
        final Movie movie = movieService.getMovie(movieId);
        return showService.createShow(movie, screen, startTime, durationInSeconds).getId();
    }

    public List<String> getAvailableSeats(@NonNull final String showId) {
        final Show show = showService.getShow(showId);
        final List<Seat> availableSeats = seatAvailabilityService.getAvailableSeats(show);
        return availableSeats.stream().map(Seat::getId).collect(Collectors.toList());
    }
}