package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String seatNumber;

    private Boolean isAvailable;

    private Boolean isVisible;

    @Column(nullable = false)
    private Integer rowIndex;

    @Column(nullable = false)
    private Integer columIndex;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @ManyToMany(mappedBy = "seats")
    private Set<Booking> bookedSeats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Set<Booking> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(Set<Booking> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColumIndex() {
        return columIndex;
    }

    public void setColumIndex(Integer columIndex) {
        this.columIndex = columIndex;
    }
}
