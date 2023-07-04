package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;

public interface PDFService {

    byte[] generatePDF(ClientData clientData);

    byte[] generatePDFByOrderId(Long id);

}
