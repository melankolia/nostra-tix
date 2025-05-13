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
}
