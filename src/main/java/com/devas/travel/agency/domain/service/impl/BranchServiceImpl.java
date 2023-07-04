package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.Branch;
import com.devas.travel.agency.domain.service.BranchService;
import com.devas.travel.agency.infrastructure.adapter.repository.BranchRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public Either<Error, List<Branch>> getBranches() {
        var items = branchRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("Branches not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);

    }
}
