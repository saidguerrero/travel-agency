package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.request.SendEmailDTO;
import com.devas.travel.agency.domain.model.Properties;
import com.devas.travel.agency.domain.service.EmailService;
import com.devas.travel.agency.infrastructure.adapter.repository.PropertiesRepository;
import com.devas.travel.agency.infrastructure.sendgrid.service.MailService;
import com.devas.travel.agency.infrastructure.utils.DynamicTemplatePersonalization;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MailService sendGridMailService;

    private final PropertiesRepository propertiesRepository;

    @Value("${bumeran.mail.send-grid-templates.reset-password-id}")
    private String templateResetPasswordId;

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
