package com.tix.nostra.nostra_tix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.dto.UpdateMovieImagePresignedDTO;
import com.tix.nostra.nostra_tix.projection.BookingListProjection;
import com.tix.nostra.nostra_tix.service.BookingService;
import com.tix.nostra.nostra_tix.service.MovieService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/middle")
public class MiddleController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/booking")
    public ResponseEntity<List<BookingListProjection>> getBookingList() {
        return ResponseEntity.ok(bookingService.findAllBookingList());
    }

    @PutMapping("/booking/{bookingId}/completed")
    public ResponseEntity<Boolean> completeBooking(@PathVariable @NotNull Long bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable @NotNull Long movieId,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, file));
    }

    @PutMapping("/movie/{movieId}/presigned-url")
    public ResponseEntity<Movie> updateMovieImage(@PathVariable @NotNull Long movieId,
            @RequestBody @Valid UpdateMovieImagePresignedDTO updateMovieImagePresignedDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, updateMovieImagePresignedDTO.imageURI()));
    }
}
