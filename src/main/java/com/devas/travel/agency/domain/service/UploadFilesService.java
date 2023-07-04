package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.request.UploadFile;
import com.devas.travel.agency.application.dto.response.Error;
import io.vavr.control.Either;

import java.util.List;

public interface UploadFilesService {

    Either<Error, String> saveUploadFile(int orderId, List<UploadFile> uploadFiles);

    byte[] getFileById(Long id);

}
