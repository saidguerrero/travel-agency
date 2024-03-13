package com.devas.travel.agency.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Integer id;

    private int statusId;

    private String quotationSheet;

    private String fullName;

    private String amount;

    private String travelInfo;

    private String supplier;

    private String reservationNumber;

    private String city;

    private String branch;

    private String salesPerson;

    private String orderDate;

    private String quoteStatus;

    private String paidStatus;

    private String contactPhoneNum;

    private String contactEmail;

    private String emergencyContactPhone;

    private String emergencyContact;

    private boolean hasFiles;

    private OrderFileResponse orderFileResponse;

    private String idString;

    private String amountWCommission;

    private int partialPaymentNumber;

    private int paymentTypeId;

    private BigDecimal amountBD;

    private BigDecimal amountDue;


}
