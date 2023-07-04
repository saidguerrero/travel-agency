package com.devas.travel.agency.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrdersAndCurrencies {

    private List<OrderResponse> orders;

    private String dollar;

    private String euro;

}
