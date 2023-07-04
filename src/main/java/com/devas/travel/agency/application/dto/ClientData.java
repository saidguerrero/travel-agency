package com.devas.travel.agency.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class ClientData {

    private String fullName;

    private BigDecimal amount;

    private String amountString;

    private String reservationNumber;

    private String travelInfo;

    private String supplier;

    private String city;

    private String branch;

    private String salesPerson;

    private String contactPhoneNum;

    private String contactEmail;

    private String emergencyContactPhone;

    private String emergencyContact;

    private int supplierId;

    private int cityId;

    private int branchId;

    private int salesPersonId;

}
