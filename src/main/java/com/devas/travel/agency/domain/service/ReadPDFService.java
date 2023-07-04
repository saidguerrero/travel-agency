package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.response.Error;
import io.vavr.control.Either;

import java.util.List;

public interface ReadPDFService {

    Either<Error,ClientData> readPDF(List<String> files64);

}
