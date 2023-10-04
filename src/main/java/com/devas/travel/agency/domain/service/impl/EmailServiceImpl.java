package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.SendEmailDTO;
import com.devas.travel.agency.domain.model.Orders;
import com.devas.travel.agency.domain.model.Properties;
import com.devas.travel.agency.domain.service.EmailService;
import com.devas.travel.agency.infrastructure.adapter.repository.PropertiesRepository;
import com.devas.travel.agency.infrastructure.sendgrid.service.MailService;
import com.devas.travel.agency.infrastructure.utils.DynamicTemplatePersonalization;
import com.devas.travel.agency.infrastructure.utils.Utils;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MailService sendGridMailService;

    private final PropertiesRepository propertiesRepository;

    @Value("${bumeran.mail.send-grid-templates.reset-password-id}")
    private String templateResetPasswordId;

    @Value("${bumeran.mail.send-grid-templates.notify-sale-id}")
    private String templateNotifySaleId;

    @Override
    public void sendEmailGeneral(SendEmailDTO sendEmailDTO) {
        var personalization = prepareDynamicTemplate(sendEmailDTO);
        sendGridMailService.sendEmail(sendEmailDTO.getTemplateId(), personalization, sendEmailDTO.getAttachments());

    }

    @Override
    public void sendResetPasswordMail(String to, String name, String code) {
        var variables = new HashMap<String, Object>();
        String linkResetPassword = propertiesRepository.findByPropertyIdAndActiveTrue("reset_password_link")
                .map(Properties::getDescription)
                .orElse(null);
        if (StringUtils.isNotBlank(linkResetPassword)) {
            variables.put("link", linkResetPassword + "?code=" + code);
            SendEmailDTO sendEmailDTO = SendEmailDTO.builder()
                    .to(to)
                    .templateId(templateResetPasswordId)
                    .variables(variables)
                    .build();
            sendEmailGeneral(sendEmailDTO);
        }
    }

    @Override
    public void sendNotifyOfSaleMail(Orders orders) {
        log.info("Enviando correo de notificación de venta");
        var variables = new HashMap<String, Object>();
        variables.put("reservation_number", orders.getReservationNumber());
        variables.put("travel_info", orders.getTravelInfo());
        variables.put("total", Utils.addingCommasToBigDecimal(orders.getAmount()));
        List<String> ccList = new ArrayList<>();
        AtomicReference<String> to = new AtomicReference<>("");

        propertiesRepository.findAll().forEach(properties -> {
            if (properties.getPropertyId().equals("notify_sale_emails_cc")) {
                ccList.addAll(Utils.stringsCharSeparatedToList(properties.getDescription(), ";"));
            } else if (properties.getPropertyId().equals("notify_sale_emails")) {
                to.set(properties.getDescription());
            }
        });

        SendEmailDTO sendEmailDTO = SendEmailDTO.builder()
                .to(to.get())
                .cc(ccList)
                .templateId(templateNotifySaleId)
                .variables(variables)
                .build();
        sendEmailGeneral(sendEmailDTO);
        log.info("se envio Correo ");
    }

    private DynamicTemplatePersonalization prepareDynamicTemplate(SendEmailDTO sendEmailDTO) {
        var tos = new Email(sendEmailDTO.getTo());
        var personalization = new DynamicTemplatePersonalization();
        personalization.addTo(tos);

        if (sendEmailDTO.getVariables() != null && !sendEmailDTO.getVariables().isEmpty()) {
            personalization.addDynamicTemplateData(sendEmailDTO.getVariables());
        }
        return personalization;

    }
}
