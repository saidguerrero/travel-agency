package com.devas.travel.agency.domain.service;

import com.twilio.rest.api.v2010.account.Message;

public interface WhatsAppService {

    Message sendWhatsAppMessageForSale(String message);

    Message sendWhatsAppMessage(String message, String phoneNumber);

}
