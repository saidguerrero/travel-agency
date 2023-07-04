package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.EuromundoService;
import com.devas.travel.agency.infrastructure.utils.Utils;

import java.math.BigDecimal;
import java.util.StringTokenizer;

public class EuromundoServiceImpl implements EuromundoService {
    @Override
    public ClientData readEuromundoPDF(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var destino = "";
        var nombre = "";
        var total = "";
        var phone = "";
        var email = "";
        var reservationNumber = "";

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino
            if (token.contains("Productos reservados")) {
                // log.info("Ruta: {}", token);
                String nextToken = tokenizer.nextToken();
                destino = tokenizer.nextToken();
            }
            //nombre del cliente
            if (token.contains("Nombre")) {
                nombre = getData(token);
            }
            if (token.contains("Paquete precio")) {
                String precio = getTotalEuro(token);
                total = precio.replace(" MXN", "");

            }
            if (token.contains("Localizador:")) {
                reservationNumber = getData(token);
            }

            if (token.contains("Teléfono")) {
                phone = getData(token);
            }

            if (token.contains("Correo")) {
                email = getData(token);
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

    public static String getData(String text) {
        var tokens = text.split(": ");
        int i = 0;
        String data = "";
        for (String token : tokens) {
            if (i == 1) {
                data = token;
            }
            i++;
        }
        return data;

    }

    public static String getTotalEuro(String text) {
        var tokens = text.split("\\$ ");
        var total = "";
        int i = 0;
        for (String token : tokens) {
            if (i == 1) {
                total = token;
            }
            i++;
        }
        return total;

    }
}
