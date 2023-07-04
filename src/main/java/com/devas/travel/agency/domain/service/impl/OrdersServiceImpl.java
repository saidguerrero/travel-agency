package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.PaginatedObjectDTO;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.*;
import com.devas.travel.agency.domain.model.Orders;
import com.devas.travel.agency.domain.service.*;
import com.devas.travel.agency.infrastructure.adapter.repository.*;
import com.devas.travel.agency.infrastructure.constants.Constants;
import com.devas.travel.agency.infrastructure.utils.Utils;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.devas.travel.agency.infrastructure.constants.Constants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    private final OrderFilesRepository orderFilesRepository;

    private final CurrencyService currencyService;

    private final CityRepository cityRepository;

    private final BranchRepository branchRepository;

    private final UserRepository userRepository;

    private final SupplierRepository supplierRepository;

    private final StatusRepository statusRepository;

    @Override
    public Either<Error, Orders> createOrder(ClientData clientData) {
        try {
            Orders orders = new Orders();
            orders.setFullName(clientData.getFullName());
            orders.setAmount(clientData.getAmount());
            orders.setReservationNumber(clientData.getReservationNumber());
            orders.setTravelInfo(clientData.getTravelInfo());
            orders.setOrderDate(LocalDateTime.now());
            orders.setContactEmail(clientData.getContactEmail());
            orders.setContactPhoneNum(clientData.getContactPhoneNum());
            orders.setEmergencyContactPhone(clientData.getEmergencyContactPhone());
            orders.setActive(Boolean.TRUE);
            cityRepository.findByCityId(clientData.getSupplierId()).ifPresent(orders::setCityByCityId);
            branchRepository.findByBranchId(clientData.getBranchId()).ifPresent(orders::setBranchByBranchId);
            userRepository.findByUserId(clientData.getSalesPersonId()).ifPresent(orders::setSalesPerson);
            supplierRepository.findBySupplierId(clientData.getSupplierId()).ifPresent(orders::setSupplierBySupplierId);
            statusRepository.findByStatusId(PENDENT_STATUS_ID).ifPresent(status -> {
                orders.setQuotaStatus(status);
                orders.setPaymentStatus(status);
            });
            ordersRepository.save(orders);
            return Either.right(orders);

        } catch (Exception e) {
            log.error("Error creating order", e);
            return Either.left(Error.builder()
                    .message("Error creating order:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }

    @Override
    public Either<Error, OrdersAndCurrencies> getAllOrders() {
        try {
            List<OrderResponse> orders = ordersRepository.findAll()
                    .stream()
                    .map(order -> {
                        boolean hasFiles = false;
                        OrderFileResponse orderFileResponse = null;
                        int idPayOrder = 0;
                        int idGeneralData = 0;
                        int idTermsAndConditions = 0;
                        var orderFiles = orderFilesRepository.findByOrdersByOrderIdOrderId(order.getOrderId());

                        if (!orderFiles.isEmpty()) {
                            hasFiles = true;
                            for (var orderFile : orderFiles) {
                                if (orderFile.getTypeFileId() == PAY_ORDER_ID) {
                                    idPayOrder = orderFile.getId().intValue();

                                } else if (orderFile.getTypeFileId() == DATA_ID) {
                                    idGeneralData = orderFile.getId().intValue();

                                } else if (orderFile.getTypeFileId() == TERMS_AND_CONDITION_ID) {
                                    idTermsAndConditions = orderFile.getId().intValue();

                                }
                            }
                            orderFileResponse = OrderFileResponse.builder()
                                    .idPayOrder(idPayOrder)
                                    .idGeneralData(idGeneralData)
                                    .idTermsAndConditions(idTermsAndConditions)
                                    .build();
                        }

                        return OrderResponse.builder()
                                .fullName(order.getFullName())
                                .city(order.getCityByCityId().getDescription())
                                .amount(Utils.addingCommasToBigDecimal(order.getAmount()))
                                .branch(order.getBranchByBranchId().getDescription())
                                .reservationNumber(order.getReservationNumber())
                                .supplier(order.getSupplierBySupplierId().getDescription())
                                .salesPerson(order.getSalesPerson().getFullName())
                                .travelInfo(order.getTravelInfo())
                                .orderDate(Utils.localDateTimeToString(order.getOrderDate(), Constants.FORMAT_DD_MM_YYYY))
                                .id(order.getOrderId().intValue())
                                .quotationSheet(Utils.leadZero(order.getOrderId()))
                                .quoteStatus(order.getQuotaStatus().getDescription())
                                .paidStatus(order.getPaymentStatus().getDescription())
                                .contactEmail(order.getContactEmail())
                                .contactPhoneNum(order.getContactPhoneNum())
                                .hasFiles(hasFiles)
                                .orderFileResponse(orderFileResponse)
                                .build();
                    }).collect(Collectors.toList());

            return Either.right(OrdersAndCurrencies.builder()
                    .orders(orders)
                    .dollar(currencyService.getDollar())
                    .euro(currencyService.getEuro())
                    .build());

        } catch (Exception e) {
            log.error("Error getting all orders", e);
            return Either.left(Error.builder()
                    .message("Error getting all orders:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }

    @Override
    public Either<Error, ClientData> getOrderById(Long id) {
        var order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return Either.right(ClientData.builder()
                .fullName(order.getFullName())
                .city(order.getCityByCityId().getDescription())
                .amount(order.getAmount())
                .branch(order.getBranchByBranchId().getDescription())
                .reservationNumber(order.getReservationNumber())
                .supplier(order.getSupplierBySupplierId().getDescription())
                .salesPerson(order.getSalesPerson().getFullName())
                .travelInfo(order.getTravelInfo())
                .contactEmail(order.getContactEmail())
                .contactPhoneNum(order.getContactPhoneNum())
                .emergencyContact(order.getEmergencyContactPhone())
                .emergencyContactPhone(order.getEmergencyContactPhone())
                .build());

    }

    private final BranchService branchService;

    private final CityService cityService;

    private final SupplierService supplierService;

    private final UserService userService;

    @Override
    public Either<Error, OrderItems> getOrderItems() {
        try {
            OrderItems orderItems = OrderItems.builder()
                    .branches(branchService.getBranches().get())
                    .cities(cityService.getCities().get())
                    .suppliers(supplierService.getSuppliers().get())
                    .salesPersons(userService.getUserByRole(List.of(SALES_PERSON_ID)).get())
                    .supplierItems(supplierService.getSupplierItems())
                    .build();
            return Either.right(orderItems);

        } catch (Exception e) {
            log.error("Error getting order items", e);
            return Either.left(Error.builder()
                    .message("Error getting order items:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }

    @Override
    public Boolean checkIfReservationExist(String reservationNumber) {
        var optional = ordersRepository.findByReservationNumber(reservationNumber);
        if (optional.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    @Override
    public Either<Error, OrdersAndCurrencies> getAllOrdersByUserId(int userId) {
        try {
            List<OrderResponse> orders = ordersRepository.findAllBySalesPersonUserIdOrderByOrderIdDesc(userId)
                    .stream()
                    .map(order -> {
                        boolean hasFiles = false;
                        OrderFileResponse orderFileResponse = null;
                        int idPayOrder = 0;
                        int idGeneralData = 0;
                        int idTermsAndConditions = 0;
                        var orderFiles = orderFilesRepository.findByOrdersByOrderIdOrderId(order.getOrderId());

                        if (!orderFiles.isEmpty()) {
                            hasFiles = true;
                            for (var orderFile : orderFiles) {
                                if (orderFile.getTypeFileId() == PAY_ORDER_ID) {
                                    idPayOrder = orderFile.getId().intValue();

                                } else if (orderFile.getTypeFileId() == DATA_ID) {
                                    idGeneralData = orderFile.getId().intValue();

                                } else if (orderFile.getTypeFileId() == TERMS_AND_CONDITION_ID) {
                                    idTermsAndConditions = orderFile.getId().intValue();

                                }
                            }
                            orderFileResponse = OrderFileResponse.builder()
                                    .idPayOrder(idPayOrder)
                                    .idGeneralData(idGeneralData)
                                    .idTermsAndConditions(idTermsAndConditions)
                                    .build();
                        }

                        return OrderResponse.builder()
                                .fullName(order.getFullName())
                                .city(order.getCityByCityId().getDescription())
                                .amount(Utils.addingCommasToBigDecimal(order.getAmount()))
                                .branch(order.getBranchByBranchId().getDescription())
                                .reservationNumber(order.getReservationNumber())
                                .supplier(order.getSupplierBySupplierId().getDescription())
                                .salesPerson(order.getSalesPerson().getFullName())
                                .travelInfo(order.getTravelInfo())
                                .orderDate(Utils.localDateTimeToString(order.getOrderDate(), Constants.FORMAT_DD_MM_YYYY))
                                .id(order.getOrderId().intValue())
                                .quotationSheet(Utils.leadZero(order.getOrderId()))
                                .quoteStatus(order.getQuotaStatus().getDescription())
                                .paidStatus(order.getPaymentStatus().getDescription())
                                .contactEmail(order.getContactEmail())
                                .contactPhoneNum(order.getContactPhoneNum())
                                .hasFiles(hasFiles)
                                .orderFileResponse(orderFileResponse)
                                .build();
                    }).collect(Collectors.toList());

            return Either.right(OrdersAndCurrencies.builder()
                    .orders(orders)
                    .dollar(currencyService.getDollar())
                    .euro(currencyService.getEuro())
                    .build());

        } catch (Exception e) {
            log.error("Error getting all orders", e);
            return Either.left(Error.builder()
                    .message("Error getting all orders:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }

    @Override
    public PaginatedObjectDTO<OrdersAndCurrencies> getPageOrdersByUserId(int userId, int page, int size) {
        var pages = ordersRepository.findBySalesPersonUserIdOrderByOrderIdDesc(userId, PageRequest.of(page, size));
        List<OrderResponse> dataList = pages.stream()
                .map(order -> {
                    boolean hasFiles = false;
                    OrderFileResponse orderFileResponse = null;
                    int idPayOrder = 0;
                    int idGeneralData = 0;
                    int idTermsAndConditions = 0;
                    var orderFiles = orderFilesRepository.findByOrdersByOrderIdOrderId(order.getOrderId());

                    if (!orderFiles.isEmpty()) {
                        hasFiles = true;
                        for (var orderFile : orderFiles) {
                            if (orderFile.getTypeFileId() == PAY_ORDER_ID) {
                                idPayOrder = orderFile.getId().intValue();

                            } else if (orderFile.getTypeFileId() == DATA_ID) {
                                idGeneralData = orderFile.getId().intValue();

                            } else if (orderFile.getTypeFileId() == TERMS_AND_CONDITION_ID) {
                                idTermsAndConditions = orderFile.getId().intValue();

                            }
                        }
                        orderFileResponse = OrderFileResponse.builder()
                                .idPayOrder(idPayOrder)
                                .idGeneralData(idGeneralData)
                                .idTermsAndConditions(idTermsAndConditions)
                                .build();
                    }

                    return OrderResponse.builder()
                            .fullName(order.getFullName())
                            .city(order.getCityByCityId().getDescription())
                            .amount(Utils.addingCommasToBigDecimal(order.getAmount()))
                            .branch(order.getBranchByBranchId().getDescription())
                            .reservationNumber(order.getReservationNumber())
                            .supplier(order.getSupplierBySupplierId().getDescription())
                            .salesPerson(order.getSalesPerson().getFullName())
                            .travelInfo(order.getTravelInfo())
                            .orderDate(Utils.localDateTimeToString(order.getOrderDate(), Constants.FORMAT_DD_MM_YYYY))
                            .id(order.getOrderId().intValue())
                            .quotationSheet(Utils.leadZero(order.getOrderId()))
                            .quoteStatus(order.getQuotaStatus().getDescription())
                            .paidStatus(order.getPaymentStatus().getDescription())
                            .contactEmail(order.getContactEmail())
                            .contactPhoneNum(order.getContactPhoneNum())
                            .hasFiles(hasFiles)
                            .orderFileResponse(orderFileResponse)
                            .build();

                }).collect(Collectors.toList());

        var orderAndConcurrencies = OrdersAndCurrencies.builder()
                .orders(dataList)
                .dollar(currencyService.getDollar())
                .euro(currencyService.getEuro())
                .build();

        return PaginatedObjectDTO.<OrdersAndCurrencies>builder()
                .data(List.of(orderAndConcurrencies))
                .pageNumber(pages.getNumber() + 1)
                .rowsOfPage(pages.getSize())
                .total(pages.getTotalElements())
                .build();

    }
}
