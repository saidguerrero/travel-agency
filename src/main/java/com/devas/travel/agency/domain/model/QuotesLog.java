package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class QuotesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotes_log_id")
    private Long id;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
