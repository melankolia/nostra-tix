package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "studio_type_id")
    private StudioType studioType;

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

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public StudioType getStudioType() {
        return studioType;
    }

    public void setStudioType(StudioType studioType) {
        this.studioType = studioType;
    }
}
