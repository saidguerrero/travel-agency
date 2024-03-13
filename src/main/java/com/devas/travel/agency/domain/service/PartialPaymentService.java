package com.devas.travel.agency.domain.service;


import com.devas.travel.agency.application.dto.request.PartialPaymentDTO;
import com.devas.travel.agency.application.dto.response.Error;
import io.vavr.control.Either;

import java.util.List;

public interface PartialPaymentService {

    Either<Error, String> createPartialPayment(PartialPaymentDTO dto);

}
