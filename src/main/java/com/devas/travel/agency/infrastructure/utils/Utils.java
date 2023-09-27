package com.devas.travel.agency.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
        if (number == null) {
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

    public static String amountRoundUp(BigDecimal number) {
        if (number == null) {
            return "N/A";
        }
        return String.format("%06d", number.setScale(0, RoundingMode.UP).intValue());

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
}
