package com.devas.travel.agency.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuoteResponse {

    @JsonProperty("id_reservacion")
    private String reservationNumber;

    @JsonProperty("nombre_titular")
    private String fullName;

    @JsonProperty("telefono")
    private String phone;

    @JsonProperty("paquete")
    private String travelInfo;

    @JsonProperty("importe_total")
    private String amount;

    @JsonProperty("importe_subtotal")
    private String subtotal;

    @JsonProperty("moneda")
    private String exchange;

    @JsonProperty("tipo_de_cambio")
    private String exchangeRate;

    @JsonProperty("importe_en_pesos")
    private String amountPesos;

}
