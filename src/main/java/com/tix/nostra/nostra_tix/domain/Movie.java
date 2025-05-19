package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String trailerURI;

    @Column(nullable = false)
    private Integer duration;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String synopsis;

    private Date showingStartDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getShowingStartDate() {
        return showingStartDate;
    }

    public void setShowingStartDate(Date showingStartDate) {
        this.showingStartDate = showingStartDate;
    }

    public String getTrailerURI() {
        return trailerURI;
    }

    public void setTrailerURI(String trailerURI) {
        this.trailerURI = trailerURI;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
