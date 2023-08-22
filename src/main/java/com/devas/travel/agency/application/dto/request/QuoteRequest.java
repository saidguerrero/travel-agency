package com.devas.travel.agency.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuoteRequest {
    @JsonProperty("id_reservacion")
    private String reservationNumber;

    @JsonProperty("importe_de_pago")
    private String amount;

    @JsonProperty("estatus_de_pago")
    private int status;

}
