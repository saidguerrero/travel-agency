package com.devas.travel.agency.domain.exception;

import java.io.Serial;

public class SendEmailIOException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SendEmailIOException(String message) {
        super(message);
    }
}
