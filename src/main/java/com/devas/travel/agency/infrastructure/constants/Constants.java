package com.devas.travel.agency.infrastructure.constants;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String OK_CODE = "OK000";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateTimeFormatter CUSTOM_FORMAT_STRING = DateTimeFormatter.ofPattern(FORMAT_STRING);
    public static final String CANCEL_INVOICE_STATUS = "cancelada";
    public static final String CANCEL_RESULT_STATUS = "cancelado";
    public static final String SUCCESS = "success";
    public static final String PATIENT_STATUTUS_ACTIVE = "Active";
    public static final String FEMALE = "Femenino";
    public static final String MALE = "Masculino";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final int TOKEN_LENGTH = 6;
    public static final String ENDPOINT = "mail/send";
    public static final String ATTACHMENT = "attachment";
    public static final String EMAIL_SUBJECT = "subject";
    public static final String FULL_NAME = "full_name";
    public static final String ORDERS = "orders/";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_ES = "es";

    public static final int ROOT_ID = 33;
    public static final int ADMIN_ID = 1;
    public static final int SALES_PERSON_ID = 2;

    public static final int PAY_ORDER_ID = 1;

    public static final int DATA_ID = 2;

    public static final int TERMS_AND_CONDITION_ID = 3;

    public static final int PENDENT_STATUS_ID = 1;

    public static final int PAY_STATUS_ID = 2;

    public static final int CANCELED_STATUS_ID = 3;

    private Constants() {
    }
}
