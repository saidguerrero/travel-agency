package com.devas.travel.agency.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class City {

    @Id
    @Column(name = "city_id")
    private int cityId;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "active")
    private boolean active;
    
}
