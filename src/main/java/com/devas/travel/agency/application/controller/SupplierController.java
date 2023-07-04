package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.UserRequest;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.SupplierService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/supplier")
@Tag(name = "Suppliers", description = "Suppliers API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class SupplierController {

    private final SupplierService service;

    @GetMapping
    public ResponseEntity<Response> getSupplier() {
        return service.getSuppliers().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }
}
