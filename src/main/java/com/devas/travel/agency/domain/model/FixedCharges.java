package com.devas.travel.agency.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class FixedCharges {

    @Id
    @Column(name = "fixed_charges_id")
    private int id;

    @Column(name = "quantity_from")
    private int quantityFrom;

    @Column(name = "quantity_to")
    private BigDecimal quantityTo;

    @Column(name = "fixed_comission")
    private BigDecimal fixedCommission;

    @Column(name = "active")
    private boolean active;

}
