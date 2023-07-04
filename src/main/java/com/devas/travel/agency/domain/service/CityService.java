package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.City;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityService {

    Either<Error, List<City>> getCities();

}
