package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;

public interface ProcessPDFService {
    ClientData processPDF(String text);

}
