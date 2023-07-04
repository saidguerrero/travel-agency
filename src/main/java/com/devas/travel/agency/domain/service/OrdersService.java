package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.PaginatedObjectDTO;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.OrderItems;
import com.devas.travel.agency.application.dto.response.OrdersAndCurrencies;
import com.devas.travel.agency.domain.model.Orders;
import io.vavr.control.Either;
import org.springframework.data.domain.Page;

public interface OrdersService {
    Either<Error, Orders> createOrder(ClientData clientData);

    Either<Error,OrdersAndCurrencies> getAllOrders();

    Either<Error,ClientData> getOrderById(Long id);

    Either<Error, OrderItems> getOrderItems();

    Boolean checkIfReservationExist(String reservationNumber);

    Either<Error,OrdersAndCurrencies> getAllOrdersByUserId(int userId);

    PaginatedObjectDTO<OrdersAndCurrencies> getPageOrdersByUserId(int userId, int page, int size);

}
