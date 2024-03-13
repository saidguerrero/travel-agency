package com.devas.travel.agency.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PartialPaymentDTO {

    private int orderId;

    private int partialPaymentNumber;

    private BigDecimal paymentAmount;

    private int paymentMethodId;

}
