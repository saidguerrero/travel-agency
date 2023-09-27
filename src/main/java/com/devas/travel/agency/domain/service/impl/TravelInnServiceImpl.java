package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.TravelInnService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;

@Slf4j
@Service
public class TravelInnServiceImpl implements TravelInnService {

    @Override
    public ClientData readTravelinnPDF(String text) throws ParseException {
        var tokenizer = new StringTokenizer(text, "\n");
        var destino = "";
        var nombre = "";
        var total = "";
        var phone = "";
        var email = "";
        var reservationNumber = "";

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //Paquete
            if (token.contains("Paquete")) {
                destino = token;
            }
            //nombre del cliente
            if (token.contains("Contacto de emergencia")) {
                nombre = tokenizer.nextToken();
            }
            if (token.contains("Monto a pagar:")) {
                total = getTotalTravelinn(tokenizer.nextToken());
            }
            if (token.contains("Localizador:")) {
                reservationNumber = tokenizer.nextToken();
            }
        }
        return ClientData.builder()
                .travelInfo(destino)
                .fullName(nombre)
                .amountString(total)
                .amount(Utils.stringToBigDecimal(total))
                .contactPhoneNum(phone)
                .contactEmail(email)
                .reservationNumber(reservationNumber)
                .build();

    }

    private String getTotalTravelinn(String price) throws ParseException {
        Locale locale = Locale.US;
        Number number = null;
        number = NumberFormat.getCurrencyInstance(locale).parse(price);
        return number.toString();

    }
}
