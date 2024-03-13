package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "travel_info")
    private String travelInfo;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "contact_phone_num")
    private String contactPhoneNum;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "active")
    private boolean active;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "amount_pesos")
    private BigDecimal amountPesos;

    @Column(name = "amount_w_comission")
    private BigDecimal amountWCommission;

    @Column(name = "sale_id")
    private String saleId;

    @Column(name = "hotel")
    private String hotel;

    @Column(name = "number_of_passengers")
    private String numberOfPassengers;

    @Column(name = "sales_note_num")
    private String salesNoteNumber;

    @Column(name = "membership_number")
    private String membershipNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "payday_limit")
    private LocalDate paydayLimit;


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

    @ManyToOne
    @JoinColumn(name = "serviceId", referencedColumnName = "service_id")
    private TypeService typeServiceById;

    @ManyToOne
    @JoinColumn(name = "paymentTypeId", referencedColumnName = "payment_type_id")
    private PaymentType paymentTypeById;

    @ManyToOne
    @JoinColumn(name = "paymentMethodId", referencedColumnName = "payment_method_id")
    private PaymentMethod paymentMethodById;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PartialPayments> partialPayments;

}
