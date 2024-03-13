package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.PaymentMethod;
import com.devas.travel.agency.domain.model.PaymentType;
import com.devas.travel.agency.domain.service.PaymentTypeService;
import com.devas.travel.agency.infrastructure.adapter.repository.PaymentMethodRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.PaymentTypeRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository repository;

    @Override
    public Either<Error, List<PaymentType>> getPaymentTypes() {
        var items = repository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("Payment Type not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);
    }
}
