package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.request.OrderFilesRequest;
import com.devas.travel.agency.application.dto.request.UploadFile;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.S3Service;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/s3")
@Tag(name = "S3", description = "S3 API")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("")
    public String uploadObject(@RequestBody String files64) {
        log.info("Uploading file");

        try {
            String filePath = "/Users/said.guerrero/Documents/hola_mundo.txt";
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return s3Service.uploadObject(bytes, "holaMundo.txt", 1);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @GetMapping
    public ResponseEntity<byte[]> getUploadFile() {
        log.info("Get uploading file");
        byte[] data =  s3Service.getFile("1/hola_mundo.txt");
        HttpHeaders header = new HttpHeaders();
        header.setContentLength(data.length);
        header.set("Content-Disposition", "attachment; filename=test.pdf");

        return new ResponseEntity<>(data, header, HttpStatus.OK);

    }
}
