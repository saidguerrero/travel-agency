package com.devas.travel.agency.infrastructure.sendgrid.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailAttachment {

    private String base64File;

    private String type;

    private String fileName;

}
