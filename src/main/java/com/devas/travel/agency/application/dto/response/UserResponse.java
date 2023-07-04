package com.devas.travel.agency.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private int userId;

    private int roleId;

    private int cityId;

    private int branchId;

    private String email;

    private String login;

    private String fullName;

    private String role;

    private String city;

    private String branch;

    private String currentDate;

}
