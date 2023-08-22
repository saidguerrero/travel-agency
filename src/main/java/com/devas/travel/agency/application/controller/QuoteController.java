package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.QuoteRequest;
import com.devas.travel.agency.application.dto.response.QuoteResponse;
import com.devas.travel.agency.domain.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/quote")
@Tag(name = "Branches", description = "Branches API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class QuoteController {

    private final QuoteService quoteService;

    @Operation(summary = "Obtener cotizacion por numero de de reservacion", description = "Endpoint para buscar los datos de una cotizacion por numero de reservacion"
            , tags = {"Cotizacion"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @PostMapping
    public QuoteResponse getQuotesByReservationNumber(@RequestBody QuoteRequest request) {
        return quoteService.getQuoteByReservationNumber(request.getReservationNumber());

    }

    @Operation(summary = "Aviso de pago", description = "Endpoint mediante el cual el cliente nos informara que ya realizo el pago de su reservacion"
            , tags = {"Cotizacion"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada o vacía"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @PostMapping("/paymentNotice")
    public HttpStatus paymentNotice(@RequestBody QuoteRequest request) {
        return quoteService.paymentNotice(request);

    }
}
