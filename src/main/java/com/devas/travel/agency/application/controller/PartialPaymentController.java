package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.PartialPaymentDTO;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.PartialPaymentService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/partialPayment")
@Tag(name = "partialPayment", description = "partialPayment API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class PartialPaymentController {

    private final PartialPaymentService service;

    @PostMapping
    public ResponseEntity<Response> createPartialPayment(@RequestBody PartialPaymentDTO dto) {
        return service.createPartialPayment(dto).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }
}
