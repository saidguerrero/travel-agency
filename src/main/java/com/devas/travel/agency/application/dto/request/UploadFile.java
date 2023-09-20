package com.devas.travel.agency.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UploadFile {

    private int fileTypeId;

    private List<String> file;

    private String fileName;

    private String fileExtension;

}
