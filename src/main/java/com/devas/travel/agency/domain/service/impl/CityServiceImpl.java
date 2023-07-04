package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.City;
import com.devas.travel.agency.domain.service.CityService;
import com.devas.travel.agency.infrastructure.adapter.repository.CityRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Either<Error, List<City>> getCities() {
        var items = cityRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("Cities not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);

    }
}
