package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.domain.service.WhatsAppService;
import com.twilio.rest.api.v2010.account.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/whatsapp")
@Tag(name = "Whatsapp", description = "whatsapp API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @GetMapping("/send")
    public Message sendWhatsAppMessage(@RequestParam String message, @RequestParam String phoneNumber) {
        return whatsAppService.sendWhatsAppMessage(message, phoneNumber);

    }
}
