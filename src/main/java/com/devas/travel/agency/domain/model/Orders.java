package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @Column(name="full_name")
    private String fullName;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="travel_info")
    private String travelInfo;

    @Column(name="reservation_number")
    private String reservationNumber;

    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Column(name="contact_phone_num")
    private String contactPhoneNum;

    @Column(name="contact_email")
    private String contactEmail;

    @Column(name="emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name="emergency_contact")
    private String emergencyContact;

    @Column(name="active")
    private boolean active;

    @Column(name="subtotal")
    private BigDecimal subtotal;

    @Column(name="exchange")
    private String exchange;

    @Column(name="exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name="amount_pesos")
    private BigDecimal amountPesos;

    @ManyToOne
    @JoinColumn(name = "supplierId", referencedColumnName = "supplier_id", nullable = false)
    private Supplier supplierBySupplierId;

    @ManyToOne
    @JoinColumn(name = "cityId", referencedColumnName = "city_id", nullable = false)
    private City cityByCityId;

    @ManyToOne
    @JoinColumn(name = "branchId", referencedColumnName = "branch_id", nullable = false)
    private Branch branchByBranchId;

    @ManyToOne
    @JoinColumn(name = "salesPersonId", referencedColumnName = "user_id", nullable = false)
    private User salesPerson;

    @ManyToOne
    @JoinColumn(name = "statusQuoteId", referencedColumnName = "status_id", nullable = false)
    private Status quotaStatus;

    @ManyToOne
    @JoinColumn(name = "statusPaymentId", referencedColumnName = "status_id", nullable = false)
    private Status paymentStatus;

}
