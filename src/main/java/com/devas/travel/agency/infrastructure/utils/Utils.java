package com.devas.travel.agency.infrastructure.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

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
        if (number == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(number.replaceAll(",", ""));
    }

    public static String dateToString(Date date, String format) {
        if (date == null) {
            return "N/A";
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);

    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);

    }

    public static String leadZero(Long number) {
        if (number == 0) {
            return "N/A";
        }
        return String.format("%04d", number);
    }

    public static String getFormatDateSpanish() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Crear un formateador de fecha personalizado
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM");

        // Formatear la fecha actual utilizando el formateador
        return fechaActual.format(formateador);

    }
}
