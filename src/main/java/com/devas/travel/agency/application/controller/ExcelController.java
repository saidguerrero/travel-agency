package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.ExcelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/excel")
@Tag(name = "excel", description = "Excel API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class ExcelController {

    private final ExcelService service;

    @Operation(summary = "Descargar archivo excel ", description = "Endpoint para crear y descargar un archivo excel con la informacion de la BD", tags = {"Admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo generado exitósamente"),
            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor", content = @Content)
    })
    @GetMapping("/orders")
    ResponseEntity<byte[]> createExcelFile(@RequestParam int id,
                                                 @RequestParam int roleId) {
        log.info("create excel");

        byte[] data =service.createExcelFile(id, roleId);
        HttpHeaders header = new HttpHeaders();
        header.setContentLength(data.length);
        header.set("Content-Disposition", "attachment; filename=test.pdf");

        return new ResponseEntity<>(data, header, HttpStatus.OK);
    }

}
