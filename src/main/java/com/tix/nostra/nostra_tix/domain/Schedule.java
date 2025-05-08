package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime showTime;
    private ZonedDateTime endShowTime;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(ZonedDateTime showTime) {
        this.showTime = showTime;
    }

    public ZonedDateTime getEndShowTime() {
        return endShowTime;
    }

    public void setEndShowTime(ZonedDateTime endShowTime) {
        this.endShowTime = endShowTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}
