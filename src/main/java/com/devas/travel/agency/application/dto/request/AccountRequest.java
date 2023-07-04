package com.devas.travel.agency.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRequest {

    private int roleId;

    private int branchId;

    private int cityId;

    private String email;

    private String login;

    private String password;

    private String fullName;
}
