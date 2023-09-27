package com.devas.travel.agency.application.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class UpdateStatus {

    private int orderId;

    private int statusId;

}
