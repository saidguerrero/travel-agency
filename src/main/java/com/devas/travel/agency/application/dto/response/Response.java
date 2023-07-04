package com.devas.travel.agency.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Response {

    /**
     * Attribute status: Guarda el resultado del request, posibles valores success o fail.
     */
    private String status;
    /**
     * Attribute code: Guarda el codigo de la operacion realizada o error.
     */
    private String code;
    /**
     * Attribute data: Guardar el resultado de la consulta realizada.
     */
    private Object result;
    /**
     * Attribute httpCode: Guardar el codigo http de la operacion realizada
     */
    private HttpStatus httpCode;

    /**
     * Attribute error: Bean que maneja un listado de errores presentados en durante el procesamiento de request.
     */
    private List<Error> error;
}
