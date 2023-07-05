package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.CurrencyRequest;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.CurrencyService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currency")
@Tag(name = "Currencies", description = "Currencies API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/dollar")
    public ResponseEntity<Response> creteDollar(@RequestBody CurrencyRequest request) {
        return currencyService.createDollar(request.getPrice(), request.getUserId()).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);

    }

    @PostMapping("/euro")
    public ResponseEntity<Response> creteEuro(@RequestBody CurrencyRequest request) {
        return currencyService.createEuro(request.getPrice(), request.getUserId()).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);

    }

    @GetMapping("/dollar")
    public ResponseEntity<Response> getDollar() {
        return ControllerUtils.getResponseSuccessOk(currencyService.getDollar());

    }

    @GetMapping("/euro")
    public ResponseEntity<Response> getEuro() {
        return ControllerUtils.getResponseSuccessOk(currencyService.getEuro());

    }
}
