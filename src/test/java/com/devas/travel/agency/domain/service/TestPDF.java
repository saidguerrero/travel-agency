package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.infrastructure.utils.Utils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class TestPDF {

    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        readPDF();
        //  System.out.println(Utils.getFormatDateSpanish());

     //   readTravelinnPDF();

        System.out.println( Utils.amountRoundUp(new BigDecimal(100.01)));
    }

    public static ClientData readPDF() {
        try {

            File file = new File("/Users/said.guerrero/Documents/BUMERAN/euro.pdf");
            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            if (text.contains("Magnicharters")) {
                readMagnichartersPDF(text);
            } else if (text.contains("Euronuevo")) {
                readEuroMundoPDF(text);
            }
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static ClientData readTravelinnPDF() {
        try {
            StringBuilder builder = new StringBuilder();
            File file = new File("/Users/said.guerrero/Documents/BUMERAN/Crear Cotizacion/travelIn_2.pdf");
            File file2 = new File("/Users/said.guerrero/Documents/BUMERAN/Crear Cotizacion/travelIn_1.pdf");
            List<File> files = List.of(file, file2);
            for (File f : files) {
                PDDocument document = Loader.loadPDF(f);
                PDFTextStripper stripper = new PDFTextStripper();
                builder.append(stripper.getText(document));
                document.close();
            }
            readTravelinnPDF2(builder.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void readEuroMundoPDF(String text) {
        var tokenizer = new StringTokenizer(text, "\n");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino
            if (token.contains("Productos reservados")) {
                // log.info("Ruta: {}", token);
                String nextToken = tokenizer.nextToken();
                String paquete = tokenizer.nextToken();
                System.out.println("Paquete: " + paquete);
            }
            //nombre del cliente
            if (token.contains("Nombre")) {
                System.out.println("nombre: " + getData(token));
            }
            if (token.contains("Paquete precio")) {
                String precio = getTotalEuro(token);
                precio = precio.replace(" MXN", "");
                System.out.println("Total: " + precio);
            }
            if (token.contains("Localizador:")) {
                System.out.println("reservacion: " + getData(token));
            }

            if (token.contains("Teléfono")) {
                System.out.println("telefono: " + getData(token));
            }

            if (token.contains("Correo")) {
                System.out.println("Correo: " + getData(token));
            }
        }
    }

    public static void readMagnichartersPDF(String text) {

        var tokenizer = new StringTokenizer(text, "\n");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            //destino
            if (token.contains("Ruta")) {
                // log.info("Ruta: {}", token);
                String tokenRuta = tokenizer.nextToken();
                System.out.println("Destino: " + getDestination(tokenRuta));
            }
            //nombre del cliente
            if (token.contains("Boleto")) {
                String tokenName = tokenizer.nextToken();
                System.out.println("nombre: " + getCustomerName(tokenName));
            }
            if (token.contains("Total: $")) {
                System.out.println("token: " + getTotal(token));
            }
            if (token.contains("Reservacion=")) {
                System.out.println("token: " + getReservation(token));
            }

        }
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

    public static String getCustomerNameEuro(String text) {
        var tokens = text.split(":");
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (i >= 1) {
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

    public static String getTotalTravelinn(String price) {
        Locale locale = Locale.US;
        Number number = null;
        try {
            number = NumberFormat.getCurrencyInstance(locale).parse(price);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(number);
        return number.toString();

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

    public static String getReservationEuro(String text) {
        var tokens = text.split(": ");
        String reservationNumber = "";
        int i = 0;
        for (String token : tokens) {
            if (i > 0) {

                reservationNumber = token;

            }
            i++;
        }
        return reservationNumber;

    }

    public static ClientData readTravelinnPDF2(String text) {
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
                System.out.println("Destion: " + destino.replace("Paquete: ", ""));
            }
            //nombre del cliente
            if (token.contains("Contacto de emergencia")) {
                nombre = tokenizer.nextToken();
                ;
            }
            if (token.contains("Monto a pagar:")) {
                total = tokenizer.nextToken();
                String precio = getTotalTravelinn(total);
                System.out.println("Total: " + precio);
            }
            if (token.contains("Localizador:")) {

                reservationNumber = tokenizer.nextToken();
            }
        }
        return ClientData.builder()
                .travelInfo(destino)
                .fullName(nombre)
                .amountString(total)
                .amount(new java.math.BigDecimal(0))
                .contactPhoneNum(phone)
                .contactEmail(email)
                .reservationNumber(reservationNumber)
                .build();

    }


}
