package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.domain.model.Properties;
import com.devas.travel.agency.domain.service.WhatsAppService;
import com.devas.travel.agency.infrastructure.adapter.repository.PropertiesRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsAppServiceImpl implements WhatsAppService {

    private final PropertiesRepository propertiesRepository;

    public static final String ACCOUNT_SID = "ACb6d6234ad68a4c8e4628dfb98661f2ee";

    public static final String AUTH_TOKEN = "5cb1f234a9f38208a42441f88e89977d";

    @Override
    public Message sendWhatsAppMessageForSale(String message) {
        log.info("enviando WhatsApp message para venta: {}", message);
        String phoneNumber = propertiesRepository.findByPropertyIdAndActiveTrue("whatsapp_phone_number")
                .map(Properties::getDescription)
                .orElse(null);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber("whatsapp:+5215586877099"),
                        new PhoneNumber("whatsapp:+14155238886"),
                        message)
                .create();

        return Message.creator(
                        new PhoneNumber("whatsapp:+521" + phoneNumber),
                        new PhoneNumber("whatsapp:+14155238886"),
                        message)
                .create();
    }

    @Override
    public Message sendWhatsAppMessage(String message, String phoneNumber) {
        log.info("Sending WhatsApp message: {}", message);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new PhoneNumber("whatsapp:+5215586877099"),
                        new PhoneNumber("whatsapp:+14155238886"),
                        message)
                .create();

        return Message.creator(
                        new PhoneNumber("whatsapp:+521" + phoneNumber),
                        new PhoneNumber("whatsapp:+14155238886"),
                        message)
                .create();
    }
}
