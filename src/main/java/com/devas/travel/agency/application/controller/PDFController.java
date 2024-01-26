package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.PDFService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pdf")
@Tag(name = "PDF", description = "PDF API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class PDFController {

    private final PDFService pdfService;

    @PostMapping
    @Operation(summary = "generar  pdf ", description = "Metodo para generar un pdf.", tags = {"PDF"})
    public ResponseEntity<byte[]> generatePDF(@RequestBody ClientData clientData) {
        log.info("Generating PDF");
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientData);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        log.info(json);

        byte[] data = pdfService.generatePDF(clientData);
        HttpHeaders header = new HttpHeaders();
        header.setContentLength(data.length);
        header.set("Content-Disposition", "attachment; filename=test.pdf");

        return new ResponseEntity<>(data, header, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generatePDFByOrderId(@PathVariable Long id) {
        log.info("Generating PDF");
        byte[] data = pdfService.generatePDFByOrderId(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentLength(data.length);
        header.set("Content-Disposition", "attachment; filename=test.pdf");

        return new ResponseEntity<>(data, header, HttpStatus.OK);

    }
}
