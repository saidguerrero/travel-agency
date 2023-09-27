package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;

import java.text.ParseException;

public interface TravelInnService {

    ClientData readTravelinnPDF(String text) throws ParseException;

}
