package com.devas.travel.agency.infrastructure.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String addingCommasToBigDecimal(BigDecimal number) {
        if (number == null) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return decimalFormat.format(number);

    }

    public static BigDecimal stringToBigDecimal(String number) {
        if (StringUtils.isEmpty (number)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(number.replace(",", ""));
    }

    public static String bigDecimalToString(BigDecimal number) {
        if (number == null) {
            return "0";
        }
        return number.toString();

    }

//    public static String dateToString(Date date, String format) {
//        if (date == null) {
//            return "N/A";
//        }
//        DateFormat formatter = new SimpleDateFormat(format);
//        return formatter.format(date);
//
//    }

    public static String dateToString(LocalDateTime date, String format) {
        if (date == null) {
            return "N/A";
        } else {
            DateFormat formatter = new SimpleDateFormat(format);
            Date dateFormatted = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
            return formatter.format(dateFormatted);
        }
    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);

    }

    public static String localDateToString(LocalDate localDate, String format) {
        if (localDate == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);

    }

    public static String leadZero(Long number, int positions) {
        if (number == 0) {
            return "N/A";
        }
        var pos = "%0" + positions + "d";
        return String.format(pos, number);
    }

    public static String amountRoundUp(BigDecimal number) {
        if (number == null) {
            return "N/A";
        }
        return  number.setScale(0, RoundingMode.UP).toString();

    }

    public static String getFormatDateSpanish() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Crear un formateador de fecha personalizado
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", new Locale("es", "ES"));

        // Formatear la fecha actual utilizando el formateador
        return fechaActual.format(formateador);

    }

    public static BigDecimal calculateSubtotal(BigDecimal total, BigDecimal taxRate) {
        // Asegurarse de que los parámetros no sean nulos o negativos
        if (total == null || taxRate == null || total.compareTo(BigDecimal.ZERO) < 0 || taxRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total y tasa de impuesto deben ser valores no nulos y no negativos.");
        }

        // Calcular el subtotal
        BigDecimal taxAmount = total.multiply(taxRate);
        return total.subtract(taxAmount);

    }

    public static BigDecimal calculateAmountPesos(BigDecimal amountRate, BigDecimal total) {
        if (amountRate == null || total == null || amountRate.compareTo(BigDecimal.ZERO) < 0 || total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total y tipo de cambio deben ser valores no nulos y no negativos.");
        }

        return total.multiply(amountRate);

    }

    public static List<String> stringsCharSeparatedToList(String string, String separator) {
        return Stream.of(string.split(separator, -1)).map(String::trim).toList();
    }

    public static String checkEmpty(BigDecimal number) {
        if (number == null) {
            return "N/A";
        }
        return  number.setScale(0, RoundingMode.UP).toString();

    }
}
