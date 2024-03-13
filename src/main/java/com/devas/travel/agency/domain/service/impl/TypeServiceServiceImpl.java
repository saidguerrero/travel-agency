package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.TypeService;
import com.devas.travel.agency.domain.service.TypeServiceService;
import com.devas.travel.agency.infrastructure.adapter.repository.TypeServiceRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceServiceImpl implements TypeServiceService {

    private final TypeServiceRepository typeServiceRepository;


    @Override
    public Either<Error, List<TypeService>> getTypeServices() {
        var items = typeServiceRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("type of services not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);
    }
}
