package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tix.nostra.nostra_tix.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAll();

    @Query("SELECT m FROM Movie m WHERE m.showingStartDate >= CURRENT_DATE")
    List<Movie> findAllUpcoming();
}
