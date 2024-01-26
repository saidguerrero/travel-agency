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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    private final BranchService branchService;

    private final CityService cityService;

    private final SupplierService supplierService;

    private final UserService userService;

    private final WhatsAppService whatsAppService;

    private final EmailService emailService;

    private final FixedChargesService fixedChargesService;

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
            orders.setEmergencyContact(clientData.getEmergencyContact());
            orders.setActive(Boolean.TRUE);
            cityRepository.findByCityId(clientData.getSupplierId()).ifPresent(orders::setCityByCityId);
            branchRepository.findByBranchId(clientData.getBranchId()).ifPresent(orders::setBranchByBranchId);
            userRepository.findByUserId(clientData.getSalesPersonId()).ifPresent(orders::setSalesPerson);
            supplierRepository.findBySupplierId(clientData.getSupplierId()).ifPresent(orders::setSupplierBySupplierId);
            statusRepository.findByStatusId(PENDENT_STATUS_ID).ifPresent(status -> {
                orders.setQuotaStatus(status);
                orders.setPaymentStatus(status);
            });
            orders.setSubtotal(Utils.calculateSubtotal(orders.getAmount(), new BigDecimal("0.16")));
            orders.setExchange(clientData.getExchange());
            if (!"MXN".equals(clientData.getExchange())) {
                orders.setExchangeRate(getExchangeRate(clientData.getExchange()));

            } else {
                orders.setExchangeRate(new BigDecimal("1"));

            }
            orders.setAmountPesos(Utils.calculateAmountPesos(orders.getAmount(), orders.getExchangeRate()));
            var fixedCharges = fixedChargesService.findByValue(clientData.getAmount());
            orders.setAmountWCommission(orders.getAmount().add(fixedCharges.getFixedCommission()));
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

    private BigDecimal getExchangeRate(String exchange) {
        if ("USD".equals(exchange)) {
            return Utils.stringToBigDecimal(currencyService.getDollar());

        } else if ("EUR".equals(exchange)) {
            return Utils.stringToBigDecimal(currencyService.getEuro());

        } else {
            return new BigDecimal("1");

        }
    }

    @Override
    public Either<Error, OrdersAndCurrencies> getAllOrders() {
        try {
            List<OrderResponse> orders = getOrderAndCurrencies(ordersRepository.findAll());

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
                .emergencyContact(order.getEmergencyContact())
                .emergencyContactPhone(order.getEmergencyContactPhone())
                .build());

    }

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
            List<OrderResponse> orders = getOrderAndCurrencies(ordersRepository.findAllBySalesPersonUserIdOrderByOrderIdDesc(userId));
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
    public PaginatedObjectDTO<OrdersAndCurrencies> getPageOrdersByUserId(String search, int userId, int page, int size, int roleId) {
        Page<Orders> pages;
        if (roleId == 1 || roleId == 33) {
            if (StringUtils.isNotBlank(search)) {
                pages = ordersRepository.findByReservationAndName(search, PageRequest.of(page - 1, size));

            } else {
                pages = ordersRepository.findOrderById(PageRequest.of(page - 1, size));

            }
        } else if (StringUtils.isNotBlank(search)) {
            pages = ordersRepository.findByReservationAndNameByIdUser(search, userId, PageRequest.of(page - 1, size));

        } else {
            pages = ordersRepository.findOrderByIdUser(userId, PageRequest.of(page - 1, size));

        }

        List<OrderResponse> dataList = getOrderAndCurrencies(pages.getContent());

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

    @Override
    public Either<Error, OrdersAndCurrencies> getPageOrdersByWord(String word, int userId) {
        List<Orders> orders = ordersRepository.findByReservationNumberLikeAndSalesPersonUserId(word, userId);
        if (orders.isEmpty()) {
            orders = ordersRepository.findByFullNameLikeAndSalesPersonUserId(word, userId);
        }
        if (orders.isEmpty()) {
            return Either.left(Error.builder()
                    .message("No se encontraron resultados")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        } else {
            List<OrderResponse> ordersConcurrencies = getOrderAndCurrencies(orders);

            return Either.right(OrdersAndCurrencies.builder()
                    .orders(ordersConcurrencies)
                    .dollar(currencyService.getDollar())
                    .euro(currencyService.getEuro())
                    .build());
        }
    }

    @Override
    public Either<Error, String> updateStatusQuote(int orderId, int statusId) {
        try {
            var order = ordersRepository.findById((long) orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            statusRepository.findByStatusId(statusId).ifPresent(order::setQuotaStatus);
            ordersRepository.save(order);
            if (PAY_STATUS_ID == statusId) {
                emailService.sendNotifyOfSaleMail(order);
                whatsAppService.sendWhatsAppMessageForSale("Se creo una compra para el localzador: " + order.getReservationNumber());
            }
            return Either.right("Status updated");

        } catch (Exception e) {
            log.error("Error updating status", e);
            return Either.left(Error.builder()
                    .message("Error updating status:" + e.getMessage())
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());

        }
    }

    @Override
    @Scheduled(cron = "0 0/15 * * * ?")
    public void updateExpiredOrder() {
        log.info("Updating expired orders");
        try {
            ordersRepository.findExpiredOrders(new Date()).forEach(order -> {
                order.setQuotaStatus(statusRepository
                        .findByStatusId(EXPIRED_STATUS_ID)
                        .orElseThrow(() -> new RuntimeException("NO se encontro el status Expirado")));
                ordersRepository.save(order);
            });

        } catch (Exception e) {
            log.error("Error updating expired orders", e);
        }
    }

    private List<OrderResponse> getOrderAndCurrencies(List<Orders> orders) {
        return orders.stream()
                .map(order -> {
                    boolean hasFiles = false;
                    OrderFileResponse orderFileResponse = null;
                    var idPayOrder = 0;
                    var idGeneralData = 0;
                    var idTermsAndConditions = 0;
                    var idConditionsOfServices = 0;
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

                            } else if (orderFile.getTypeFileId() == CONDITIONS_SERVICES_ID) {
                                idConditionsOfServices = orderFile.getId().intValue();

                            }
                        }
                        orderFileResponse = OrderFileResponse.builder()
                                .idPayOrder(idPayOrder)
                                .idGeneralData(idGeneralData)
                                .idTermsAndConditions(idTermsAndConditions)
                                .idConditionsOfServices(idConditionsOfServices)
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
                            .quotationSheet(Utils.leadZero(order.getOrderId(), 4))
                            .quoteStatus(order.getQuotaStatus().getDescription())
                            .paidStatus(order.getPaymentStatus().getDescription())
                            .contactEmail(order.getContactEmail())
                            .contactPhoneNum(order.getContactPhoneNum())
                            .emergencyContactPhone(order.getEmergencyContactPhone())
                            .emergencyContact(order.getEmergencyContact())
                            .hasFiles(hasFiles)
                            .orderFileResponse(orderFileResponse)
                            .statusId(order.getQuotaStatus().getStatusId())
                            .idString(Utils.leadZero(order.getOrderId(), 4))
                            .amountWCommission( Utils.addingCommasToBigDecimal(order.getAmountWCommission()))
                            .build();
                }).toList();
    }
}
