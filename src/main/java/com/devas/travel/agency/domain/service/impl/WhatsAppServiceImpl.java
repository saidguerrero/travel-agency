package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.domain.service.WhatsAppService;
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

    public static final String ACCOUNT_SID = "ACb6d6234ad68a4c8e4628dfb98661f2ee";

    public static final String AUTH_TOKEN = "79816e1f944ad5a812fa1a2f1d6a9d25";

    @Override
    public Message sendWhatsAppMessage(String message, String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        return Message.creator(
                        new PhoneNumber("whatsapp:+521" + phoneNumber),
                        new PhoneNumber("whatsapp:+14155238886"),
                        message)
                .create();

    }
}
