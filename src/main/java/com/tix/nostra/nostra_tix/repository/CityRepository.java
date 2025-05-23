package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;

import com.tix.nostra.nostra_tix.domain.City;
import com.tix.nostra.nostra_tix.dto.TheaterCountCity;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAll();

    @Query(value = "SELECT new com.tix.nostra.nostra_tix.dto.TheaterCountCity(c.id, c.name, COUNT(t)) " +
            "FROM Theater t JOIN t.city c " +
            "GROUP BY c.id, c.name")
    List<TheaterCountCity> findAllTheaterCount();

}
