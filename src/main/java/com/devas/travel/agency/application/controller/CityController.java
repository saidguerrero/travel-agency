package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.CityService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
@Tag(name = "Cities", description = "Cities API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class CityController {

    private final CityService service;

    @GetMapping
    public ResponseEntity<Response> getCities() {
        return service.getCities().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }
}
