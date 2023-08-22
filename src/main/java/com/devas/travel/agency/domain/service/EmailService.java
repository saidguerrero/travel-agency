package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.request.SendEmailDTO;

public interface EmailService {

    void sendEmailGeneral(SendEmailDTO sendEmailDTO);

    void sendResetPasswordMail(String to, String name, String code);

}
