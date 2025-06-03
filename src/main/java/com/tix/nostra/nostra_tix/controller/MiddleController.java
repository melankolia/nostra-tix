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
import com.tix.nostra.nostra_tix.dto.ResultResponseDTO;
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
    public ResponseEntity<ResultResponseDTO<List<BookingListProjection>>> getBookingList() {

        List<BookingListProjection> result = bookingService.findAllBookingList();

        ResultResponseDTO<List<BookingListProjection>> response = new ResultResponseDTO<>("OK", result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/booking/{bookingId}/completed")
    public ResponseEntity<ResultResponseDTO<Boolean>> completeBooking(@PathVariable @NotNull Long bookingId) {
        Boolean result = bookingService.completeBooking(bookingId);

        ResultResponseDTO<Boolean> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/movie/{movieId}")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovie(@PathVariable @NotNull Long movieId,
            @RequestParam("file") MultipartFile file) {
        Movie result = movieService.updateMovie(movieId, file);

        ResultResponseDTO<Movie> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/movie/{movieId}/presigned-url")
    public ResponseEntity<ResultResponseDTO<Movie>> updateMovieImage(@PathVariable @NotNull Long movieId,
            @RequestBody @Valid UpdateMovieImagePresignedDTO updateMovieImagePresignedDTO) {

        Movie result = movieService.updateMovie(movieId, updateMovieImagePresignedDTO.imageURI());

        ResultResponseDTO<Movie> response = new ResultResponseDTO<>("OK", result);

        return ResponseEntity.ok(response);
    }
}
