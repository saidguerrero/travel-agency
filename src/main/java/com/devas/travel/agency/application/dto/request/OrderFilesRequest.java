package com.devas.travel.agency.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderFilesRequest {

    private int orderId;

    private List<UploadFile> uploadFiles;

}
