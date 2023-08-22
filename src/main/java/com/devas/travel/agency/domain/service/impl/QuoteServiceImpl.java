package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.QuoteRequest;
import com.devas.travel.agency.application.dto.response.QuoteResponse;
import com.devas.travel.agency.domain.model.PaymentNoticeLog;
import com.devas.travel.agency.domain.model.QuotesLog;
import com.devas.travel.agency.domain.service.QuoteService;
import com.devas.travel.agency.infrastructure.adapter.repository.OrdersRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.PaymentNoticeLogRepository;
import com.devas.travel.agency.infrastructure.adapter.repository.QuotesLogRepository;
import com.devas.travel.agency.infrastructure.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final OrdersRepository ordersRepository;

    private final QuotesLogRepository quotesLogRepository;

    private final PaymentNoticeLogRepository paymentNoticeLogRepository;

    @Override
    public QuoteResponse getQuoteByReservationNumber(String reservationNumber) {
        QuotesLog quotesLog = new QuotesLog();
        quotesLog.setReservationNumber(reservationNumber);
        quotesLog.setCreateDate(LocalDateTime.now());
        quotesLogRepository.save(quotesLog);
        return ordersRepository.findByReservationNumber(reservationNumber).map(
                order -> QuoteResponse.builder()
                        .reservationNumber(order.getReservationNumber())
                        .fullName(order.getFullName())
                        .phone(order.getContactPhoneNum())
                        .travelInfo(order.getTravelInfo())
                        .amount(Utils.bigDecimalToString(order.getAmount()))
                        .amountPesos(Utils.bigDecimalToString(order.getAmountPesos()))
                        .exchange(order.getExchange())
                        .subtotal(Utils.bigDecimalToString(order.getSubtotal()))
                        .build()
        ).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public HttpStatus paymentNotice(QuoteRequest quoteRequest) {
        try {
            PaymentNoticeLog paymentNoticeLog = new PaymentNoticeLog();
            paymentNoticeLog.setReservationNumber(quoteRequest.getReservationNumber());
            //solamente nos respondera en pesos
            paymentNoticeLog.setPaymentAmount(Utils.stringToBigDecimal(quoteRequest.getAmount()));
            paymentNoticeLog.setEstatus(1);
            paymentNoticeLog.setCreateDate(LocalDateTime.now());
            paymentNoticeLogRepository.save(paymentNoticeLog);
            return HttpStatus.OK;

        } catch (Exception e) {
            log.error("Error al guardar el log de payment notice: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;

        }
    }
}
