package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Euro {

    @Id
    @Column(name = "euro_id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "last_price")
    private Double lastPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "active")
    private boolean active;

}
