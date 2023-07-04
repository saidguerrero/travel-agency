package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.OrdersService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
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

    @GetMapping
    public ResponseEntity<Response> getOrders() {
        return ordersService.getAllOrders().fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }

    @GetMapping("/byUserId/{id}")
    public ResponseEntity<Response> getOrdersByUserId(@PathVariable int id) {
        return ordersService.getAllOrdersByUserId(id).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }

    @GetMapping("/user/{id}/page/{page}/size/{size}")
    public ResponseEntity<Response> getPageOrders(@PathVariable int id, @PathVariable int page, @PathVariable int size) {
        return ControllerUtils.getResponseSuccessOk(
                ordersService.getPageOrdersByUserId(id, page, size));

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
}
