package com.devas.travel.agency.infrastructure.sendgrid.service;

import com.devas.travel.agency.infrastructure.sendgrid.dto.request.SendEmailAttachment;
import com.devas.travel.agency.infrastructure.utils.DynamicTemplatePersonalization;
import io.vavr.control.Try;

import java.util.List;

public interface MailService {

    Try<String> sendEmail(String templateId, DynamicTemplatePersonalization personalization, List<SendEmailAttachment> sendEmailAttachments);
}
