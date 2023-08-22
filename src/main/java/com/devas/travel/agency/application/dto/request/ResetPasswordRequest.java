package com.devas.travel.agency.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPasswordRequest {

    private String code;

    private String password;

    private String newPassword;

    private String oldPassword;

    private String email;

    private String newEmail;

}
