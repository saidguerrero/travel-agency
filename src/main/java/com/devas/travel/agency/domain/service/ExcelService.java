package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import io.vavr.control.Either;

public interface ExcelService {

    byte[] createExcelFile(int userId, int roleId);

}
