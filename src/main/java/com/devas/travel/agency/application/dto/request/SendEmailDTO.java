package com.devas.travel.agency.application.dto.request;

import com.devas.travel.agency.infrastructure.sendgrid.dto.request.SendEmailAttachment;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SendEmailDTO {

    private String templateId;

    private String to;

    private List<String> cc;

    private Map<String, Object> variables;

    private List<SendEmailAttachment> attachments;

}
