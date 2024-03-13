package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.PaymentMethod;
import com.devas.travel.agency.domain.service.PaymentMethodService;
import com.devas.travel.agency.infrastructure.adapter.repository.PaymentMethodRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Either<Error, List<PaymentMethod>> getPaymentMethods() {
        var items = paymentMethodRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("Branches not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);
    }
}
