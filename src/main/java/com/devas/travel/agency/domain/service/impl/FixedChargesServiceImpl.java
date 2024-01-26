package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.domain.model.FixedCharges;
import com.devas.travel.agency.domain.service.FixedChargesService;
import com.devas.travel.agency.infrastructure.adapter.repository.FixedChargesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FixedChargesServiceImpl implements FixedChargesService {

    private final FixedChargesRepository fixedChargesRepository;

    @Override
    public FixedCharges findByValue(BigDecimal value) {
        return fixedChargesRepository.findByValue(value)
                .orElse(null);

    }
}
