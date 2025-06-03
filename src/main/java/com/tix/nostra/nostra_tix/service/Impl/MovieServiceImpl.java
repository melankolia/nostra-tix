package com.tix.nostra.nostra_tix.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
import com.tix.nostra.nostra_tix.dto.MovieRequestDTO;
import com.tix.nostra.nostra_tix.exception.ResourceNotFoundException;
import com.tix.nostra.nostra_tix.repository.MovieRepository;
import com.tix.nostra.nostra_tix.service.FileService;
import com.tix.nostra.nostra_tix.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FileService fileService;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findAllUpcoming() {
        return movieRepository.findAllUpcoming();
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    @Override
    public Movie createMovie(MovieRequestDTO movieRequestDTO) {
        Movie movie = new Movie();
        movie.setName(movieRequestDTO.name());
        movie.setTrailerURI(movieRequestDTO.trailerURI());
        movie.setImageURI(movieRequestDTO.imageURI());
        movie.setDuration(movieRequestDTO.duration());
        movie.setSynopsis(movieRequestDTO.synopsis());
        movie.setShowingStartDate(new Date(movieRequestDTO.showingStartDate() * 1000));
        movie.setShowingEndDate(new Date(movieRequestDTO.showingEndDate() * 1000));

        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, MovieRequestDTO movieRequestDTO) {
        Movie existingMovie = findById(id);

        existingMovie.setName(movieRequestDTO.name());
        existingMovie.setTrailerURI(movieRequestDTO.trailerURI());
        existingMovie.setImageURI(movieRequestDTO.imageURI());
        existingMovie.setDuration(movieRequestDTO.duration());
        existingMovie.setSynopsis(movieRequestDTO.synopsis());
        existingMovie.setShowingStartDate(new Date(movieRequestDTO.showingStartDate() * 1000));
        existingMovie.setShowingEndDate(new Date(movieRequestDTO.showingEndDate() * 1000));

        return movieRepository.save(existingMovie);
    }

    @Override
    public Movie updateMovie(Long movieId, MultipartFile file) {
        Movie existingMovie = findById(movieId);

        try {
            String filePath = fileService.uploadFile(file);
            existingMovie.setImageURI(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }

        return movieRepository.save(existingMovie);
    }

    @Override
    public Movie updateMovie(Long movieId, String imageURI) {
        Movie existingMovie = findById(movieId);
        existingMovie.setImageURI(imageURI);
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }
}
