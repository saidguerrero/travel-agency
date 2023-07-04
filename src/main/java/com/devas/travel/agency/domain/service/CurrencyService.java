package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import io.vavr.control.Either;

public interface CurrencyService {

    Either<Error, String> createDollar(String dollarPrice, int userId);

    Either<Error, String> createEuro(String euroPrice, int userId);

    String getDollar();

    String getEuro();

}
