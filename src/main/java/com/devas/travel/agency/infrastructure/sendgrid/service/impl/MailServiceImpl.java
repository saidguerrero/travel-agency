package com.devas.travel.agency.infrastructure.sendgrid.service.impl;

import com.devas.travel.agency.domain.exception.SendEmailIOException;
import com.devas.travel.agency.infrastructure.sendgrid.dto.request.SendEmailAttachment;
import com.devas.travel.agency.infrastructure.sendgrid.service.MailService;
import com.devas.travel.agency.infrastructure.utils.DynamicTemplatePersonalization;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Email;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Wizeline: gerardo.lucas
 * Description: Mail service implementation for SendGrid
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final SendGridAPI sendGridAPI;

    @Value("${bumeran.mail.sender}")
    private String sender;
    @Value("${bumeran.mail.from-name}")
    private String fromMail;

    private String executeSendEmail(String templateId, DynamicTemplatePersonalization personalization, List<SendEmailAttachment> sendEmailAttachments) {
        // Create Sender email
        var from = new Email(sender, fromMail);
        // Mail config object to add template_id subject, dynamic data, from, to, etc.
        var mail = new Mail();
        // Add sender in mail config
        mail.setFrom(from);
        // Add dynamic data and receiver email into DynamicTemplatePersonalization object
        // then add it in mail config
        mail.addPersonalization(personalization);
        // Add template_id in mail config
        mail.setTemplateId(templateId);

        // Add attachments in mail config if sendEmailAttachments list is not empty
        if (sendEmailAttachments != null && !sendEmailAttachments.isEmpty()) {
            attachFiles(sendEmailAttachments, mail);
        }

        // Create request object to execute sender email
        var request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            var response = sendGridAPI.api(request);
            log.info(response.getBody());
            log.info(response.getHeaders().toString());
            log.info(String.valueOf(response.getStatusCode()));

            return response.getBody();

        } catch (IOException e) {
            log.error("executeSendEmail: {}", e.getMessage());
            throw new SendEmailIOException(e.getMessage());

        }
    }

    /**
     * Method to add all file as attachment in mail config
     *
     * @param sendEmailAttachments List of files with their type
     * @param mail                 Mail config object
     */
    private void attachFiles(List<SendEmailAttachment> sendEmailAttachments, Mail mail) {
        sendEmailAttachments.forEach(attachment -> {
            var attachments = new Attachments();
            attachments.setFilename(attachment.getFileName());
            attachments.setType(attachment.getType());
            attachments.setDisposition("attachment");
            attachments.setContent(attachment.getBase64File());
            // Add attachment in mail config
            mail.addAttachments(attachments);
        });
    }

    @Override
    public Try<String> sendEmail(String templateId, DynamicTemplatePersonalization personalization, List<SendEmailAttachment> sendEmailAttachments) {
        return Try.of(() -> executeSendEmail(templateId, personalization, sendEmailAttachments));
    }
}