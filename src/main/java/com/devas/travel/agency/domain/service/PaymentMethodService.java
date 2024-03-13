package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.PaymentMethod;
import io.vavr.control.Either;

import java.util.List;

public interface PaymentMethodService {

    Either<Error, List<PaymentMethod>> getPaymentMethods();

}
