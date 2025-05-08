package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;

    private String name;

    private ZonedDateTime showingStartDate;

    private ZonedDateTime showingEndDate;

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

    public ZonedDateTime getShowingStartDate() {
        return showingStartDate;
    }

    public void setShowingStartDate(ZonedDateTime showingStartDate) {
        this.showingStartDate = showingStartDate;
    }

    public ZonedDateTime getShowingEndDate() {
        return showingEndDate;
    }

    public void setShowingEndDate(ZonedDateTime showingEndDate) {
        this.showingEndDate = showingEndDate;
    }
}
