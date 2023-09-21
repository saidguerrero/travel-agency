package com.devas.travel.agency.infrastructure.utils;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static com.devas.travel.agency.infrastructure.constants.Constants.OK_CODE;
import static com.devas.travel.agency.infrastructure.constants.Constants.SUCCESS;

public class ControllerUtils {

    public static final String FAIL = "fail";

    private ControllerUtils() {
    }

    public static ResponseEntity<Response> getResponseSuccessOk(Object result) {
        Response response = new Response();
        response.setResult(result);
        response.setStatus(SUCCESS);
        response.setCode(OK_CODE);
        response.setHttpCode(HttpStatus.OK);

        return new ResponseEntity<>(response, response.getHttpCode());
    }

    public static ResponseEntity<Response>  getResponseError(Error error) {
        var response = new Response();

        response.setHttpCode(error.getHttpStatus());
        response.setStatus(FAIL);
        var errors = new ArrayList<Error>();
        errors.add(error);
        response.setError(errors);

        return new ResponseEntity<>(response, error.getHttpStatus());
    }
}
