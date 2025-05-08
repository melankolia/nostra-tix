package com.tix.nostra.nostra_tix.domain;

import jakarta.persistence.*;

@Entity
@Table
public class StudioType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer row_size;

    private Integer column_size;

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

    public Integer getRow_size() {
        return row_size;
    }

    public void setRow_size(Integer row_size) {
        this.row_size = row_size;
    }

    public Integer getColumn_size() {
        return column_size;
    }

    public void setColumn_size(Integer column_size) {
        this.column_size = column_size;
    }
}
