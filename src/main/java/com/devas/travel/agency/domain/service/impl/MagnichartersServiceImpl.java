package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.ProcessPDFService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

@Slf4j
@Service
@Qualifier("magnichartersServiceImpl")
@RequiredArgsConstructor
public class MagnichartersServiceImpl implements ProcessPDFService {

    @Override
    public ClientData processPDF(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var destino = "";
        var nombre = "";
        var total = "";
        var reservationNumber = "";

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.contains("Ruta")) {
                String tokenRuta = tokenizer.nextToken();
                destino = getDestination(tokenRuta);

            }
            if (token.contains("Boleto")) {
                String tokenName = tokenizer.nextToken();
                nombre = getCustomerName(tokenName);

            }
            if (token.contains("Total: $")) {
                total = getTotal(token);

            }
            if (token.contains("Reservacion=")) {
                reservationNumber = getReservation(token);

            }

        }
        return ClientData.builder()
                .travelInfo(destino)
                .fullName(nombre)
                .amount(Utils.stringToBigDecimal(total))
                .reservationNumber(reservationNumber)
                .build();

    }

    public static String getDestination(String text) {
        var tokens = text.split(" ");
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (i <= 2) {
                sb.append(token).append(" ");
            }
            i++;
        }
        return sb.toString();

    }

    public static String getCustomerName(String text) {
        var tokens = text.split(" ");
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (i >= 3) {
                sb.append(token).append(" ");
            }
            i++;
        }
        return sb.deleteCharAt(sb.length() - 1).toString();

    }

    public static String getTotal(String text) {
        var tokens = text.split("\\$");
        var total = "";
        for (String token : tokens) {
            total = token;
        }
        return total;

    }

    public static String getReservation(String text) {
        var tokens = text.split("=");
        String reservation = "";
        int i = 0;
        for (String token : tokens) {
            if (i > 0) {
                var tokenReservation = token.split(" ");
                if (tokenReservation.length > 0) {
                    reservation = tokenReservation[0];
                }
            }
            i++;
        }
        return reservation;

    }
}
