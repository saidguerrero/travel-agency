package com.devas.travel.agency.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {

    private String userLogin;

    private String password;
}
