package com.devas.travel.agency.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderFileResponse {

    private int idPayOrder;

    private int idGeneralData;

    private int idTermsAndConditions;

    private int idConditionsOfServices;

}
