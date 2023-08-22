package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PaymentNoticeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_notice_id")
    private Long id;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Column(name = "estatus")
    private int estatus;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
