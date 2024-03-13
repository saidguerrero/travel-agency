package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.TypeService;
import io.vavr.control.Either;

import java.util.List;

public interface TypeServiceService {

    Either<Error, List<TypeService>> getTypeServices();

}
