package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.OrdersService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Orders API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(summary = "Obtener cotizaciones",
            description = "Endpoint para obtener las cotizaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada o vacía"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Response> getOrders() {
        return ordersService.getAllOrders().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }

    @Operation(summary = "Obtener cotizaciones ",
            description = "Endpoint para obtener las cotizaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada o vacía"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @GetMapping("/byUserId/{id}")
    public ResponseEntity<Response> getOrdersByUserId(@PathVariable int id) {
        return ordersService.getAllOrdersByUserId(id).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }

    @Operation(summary = "Buscar cotizaciones",
            description = "Endpoint para buscar cotizaciones por vendor o por nombre del cliente o numero de reservacion , paginadas"
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información encontrada o vacía"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @GetMapping("/pageOrders")
    public ResponseEntity<Response> getPageOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) int id, @RequestParam int page, @RequestParam int size) {
        return ControllerUtils.getResponseSuccessOk(
                ordersService.getPageOrdersByUserId(search, id, page, size));

    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrderById(@PathVariable Long id) {
        return ordersService.getOrderById(id).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);

    }

    @GetMapping("/items")
    public ResponseEntity<Response> getOrderItems() {
        return ordersService.getOrderItems().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk);

    }

    @GetMapping("/checkReservation/{reservation}")
    public ResponseEntity<Response> checkReservation(@PathVariable String reservation) {
        return ControllerUtils.getResponseSuccessOk(
                ordersService.checkIfReservationExist(reservation));

    }

    @GetMapping("/findbyword/{word}/user/{userId}")
    public ResponseEntity<Response> getOrdersByWord(@PathVariable String word, @PathVariable int userId) {
        return ordersService.getAllOrders().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }
}
