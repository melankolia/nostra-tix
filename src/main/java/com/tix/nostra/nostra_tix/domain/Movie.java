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

    private String imageURI;

    @Column(nullable = false)
    private Integer duration;

    @Column(columnDefinition = "text")
    private String synopsis;

    private Date showingStartDate;

    private Date showingEndDate;

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

    public Date getShowingStartDate() {
        return showingStartDate;
    }

    public void setShowingStartDate(Date showingStartDate) {
        this.showingStartDate = showingStartDate;
    }

    public Date getShowingEndDate() {
        return showingEndDate;
    }

    public void setShowingEndDate(Date showingEndDate) {
        this.showingEndDate = showingEndDate;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}
