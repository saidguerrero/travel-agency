package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.ClientData;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.StringTokenizer;

public class TestPDF {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        readPDF();
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
}
