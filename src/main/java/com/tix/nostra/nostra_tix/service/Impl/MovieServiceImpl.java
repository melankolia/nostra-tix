package com.tix.nostra.nostra_tix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tix.nostra.nostra_tix.domain.Movie;
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
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie updateMovie(Long movieId, MultipartFile file) {
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

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
        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        existingMovie.setImageURI(imageURI);
        return movieRepository.save(existingMovie);
    }
}
