package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.ProcessPDFService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

@Service
@Qualifier("newTrevelInnServiceImpl")
public class NewTrevelInnServiceImpl implements ProcessPDFService {


    @Override
    public ClientData processPDF(String text) {
        String destino = "";
        String nombre = "";
        String total = "";
        String phone = "";
        String email = "";
        String reservationNumber = "";
        if (text.contains("Renta de auto")) {
            destino = rentaDeAuto(text);

        } else if (text.contains("Vuelo")) {
            destino = getDataVuelo(text);

        } else if (text.contains("Tour")) {
            destino = getDataTour(text);

        } else if (text.contains("hotel-reservar")) {
            destino = getDataHotel(text);
        }

        if (text.contains("/presupuesto-traslado")) {
            total = getTotalTranslado(text);

        }

        var tokenizer = new StringTokenizer(text, "\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            //para hoteles
            if (token.contains("Hotel") && destino.isEmpty()) {
                destino = getHotel(token, "\\$ ");

            } else if (token.contains("Paquete") && destino.isEmpty()) {
                destino = tokenizer.nextToken();
            }
//            else if (token.contains("Tour") && destino.isEmpty()) {
//                destino = tokenizer.nextToken();
//            }
            else if (token.contains("Traslado") && destino.isEmpty()) {
                destino = tokenizer.nextToken();
            } else if (token.contains("Dirección") && destino.isEmpty()) {
                var temp = tokenizer.nextToken();
                destino = tokenizer.nextToken();
            }

            //nombre del cliente
            if (token.contains("Titular de la reservación")) {
                nombre = getName(token, ":");
            }
            if ((token.contains("Total Pesos") || token.contains("Total pesos")) && total.isEmpty()) {
                String precio = getTotalEuro(token);
                total = precio.replace(" MXN", "");

            }
            if (token.contains("Localizador")) {
                reservationNumber = getDataDestination(token, " ");
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

    public String rentaDeAuto(String text) {
        var resp = "";
        var tokenizer = new StringTokenizer(text, "\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            if (token.contains("Atención a clientes")) {
                var temp = tokenizer.nextToken();
                var temp2 = tokenizer.nextToken();
                resp = tokenizer.nextToken();
            }
        }
        return resp;

    }

    public String getDataVuelo(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var resp = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            if (token.contains("Atención a clientes")) {
                var temp = tokenizer.nextToken();
                var temp2 = tokenizer.nextToken();
                resp = tokenizer.nextToken();
            }
        }
        return resp;
    }

    public String getDataHotel(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var resp = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            if (token.contains("Atención a clientes")) {
                var temp = tokenizer.nextToken();
                var temp2 = tokenizer.nextToken();
                resp = tokenizer.nextToken();
            }
        }
        return resp;
    }

    public String getDataTour(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var resp = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            if (token.contains("Tour")) {

                resp = token;
            }
        }
        return resp;
    }

    public String getTotalTranslado(String text) {
        var tokenizer = new StringTokenizer(text, "\n");
        var resp = "";
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino

            if (token.contains("Viaje redondo")) {
                var temp = token.split("\\$ ");
                resp = temp[1];

            }
        }
        return resp;
    }

    public static String getData(String text, String regex) {
        var tokens = text.split(regex);
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

    public static String getDataDestination(String text, String regex) {
        var tokens = text.split(regex);
        int i = 0;
        String data = "";
        for (String token : tokens) {
            if (i == 3) {
                data = token;
            }
            i++;
        }
        return data;

    }

    public static String getName(String text, String regex) {
        var tokens = text.split(regex);
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

    public static String getHotel(String text, String regex) {
        var tokens = text.split(regex);
        int i = 0;
        String data = "";
        for (String token : tokens) {
            if (i == 0) {
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
