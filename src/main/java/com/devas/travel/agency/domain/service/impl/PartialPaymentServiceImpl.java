package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.PartialPaymentDTO;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.PartialPayments;
import com.devas.travel.agency.domain.service.PartialPaymentService;
import com.devas.travel.agency.infrastructure.adapter.repository.OrdersRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.PartialPaymentRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.PaymentMethodRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartialPaymentServiceImpl implements PartialPaymentService {

    private final PartialPaymentRepository repository;

    private final OrdersRepository ordersRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public Either<Error, String> createPartialPayment(PartialPaymentDTO dto) {
        try {
            PartialPayments partialPayment = new PartialPayments();
            partialPayment.setPaymentAmount(dto.getPaymentAmount());
            partialPayment.setPartialPaymentNumber(dto.getPartialPaymentNumber());
            partialPayment.setPaymentDate(LocalDateTime.now());
            ordersRepository.findById((long) dto.getOrderId()).ifPresent(partialPayment::setOrder);
            paymentMethodRepository.findById(1).ifPresent(partialPayment::setPaymentMethod);
            repository.save(partialPayment);
            return Either.right("partial payment created");

        } catch (Exception e) {
            log.error("Error partial payment created", e);
            return Either.left(Error.builder()
                    .message("Error partial payment created:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }
}
