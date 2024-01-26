package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.domain.model.FixedCharges;

import java.math.BigDecimal;


public interface FixedChargesService {

    FixedCharges findByValue(BigDecimal value);

}
