package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.request.QuoteRequest;
import com.devas.travel.agency.application.dto.response.QuoteResponse;
import org.springframework.http.HttpStatus;

public interface QuoteService {

    QuoteResponse getQuoteByReservationNumber(String reservationNumber);

    HttpStatus paymentNotice(QuoteRequest quoteRequest);

}
