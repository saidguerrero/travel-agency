package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.model.Branch;
import io.vavr.control.Either;

import java.util.List;

public interface BranchService {

    Either<Error, List<Branch>> getBranches();

}
