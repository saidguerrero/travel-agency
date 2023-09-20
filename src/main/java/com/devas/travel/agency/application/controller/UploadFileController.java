package com.devas.travel.agency.application.controller;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.request.OrderFilesRequest;
import com.devas.travel.agency.application.dto.request.UploadFile;
import com.devas.travel.agency.application.dto.response.Response;
import com.devas.travel.agency.domain.service.ReadPDFService;
import com.devas.travel.agency.domain.service.UploadFilesService;
import com.devas.travel.agency.infrastructure.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/upload")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class UploadFileController {

    private final UploadFilesService uploadFilesService;

    private final ReadPDFService readPDFService;

    @PostMapping("/aditional")
    public ResponseEntity<Response> saveUploadFiles(@RequestBody OrderFilesRequest request) {
        log.info("Saving uploading files");
        List<UploadFile> uploadFiles = request.getUploadFiles();
        return uploadFilesService.saveUploadFile(request.getOrderId(), uploadFiles).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );

    }

    @PostMapping(consumes = {"multipart/form-data"})
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file");
        if (!file.isEmpty()) {
            log.info("File is not empty");
        }
        try {
            PDDocument document = Loader.loadPDF(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return "PDF read successfully";

    }

    @PostMapping("/base64")
    public String uploadFileBase64(@RequestBody String files64) {
        log.info("Uploading file");
        if (!files64.isEmpty()) {
            log.info("File is not empty");
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(files64);
            PDDocument document = Loader.loadPDF(bytes);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);

        }
        return "PDF read successfully";

    }

    @PostMapping("/readQuotePDF")
    public ResponseEntity<Response> readQuote(@RequestBody List<String> files64) {
        return readPDFService.readPDF(files64).fold(
                ControllerUtils::getResponseError,
                ControllerUtils::getResponseSuccessOk
        );
    }

    @GetMapping("/{orderFileId}")
    public ResponseEntity<byte[]> getUploadFile(@PathVariable Long orderFileId) {
        log.info("Get uploading file");
        byte[] data = uploadFilesService.getFileById(orderFileId);
        HttpHeaders header = new HttpHeaders();
        header.setContentLength(data.length);
        header.set("Content-Disposition", "attachment; filename=test.pdf");

        return new ResponseEntity<>(data, header, HttpStatus.OK);

    }

    @GetMapping("/readTest/{fileName}")
    public ResponseEntity<Response> readtest(@PathVariable String fileName) {
        ClientData clientData = null;
        if (fileName != null && fileName.contains("magni")) {
            clientData = ClientData.builder()
                    .amount(new BigDecimal("5537.25"))
                    .reservationNumber("WAD19134V35")
                    .travelInfo("MEX a HUX")
                    .fullName("CRUZ BETANZOS ANGEL")
                    .supplier("MAGNICHARTERS")
                    .salesPerson("")
                    .branch("")
                    .city("")
                    .contactEmail("")
                    .contactPhoneNum("")

                    .build();
        }
        if (fileName != null && fileName.contains("Euronuevo")) {
            clientData = ClientData.builder()
                    .amount(new BigDecimal("22851.98"))
                    .reservationNumber("D44MJZ")
                    .travelInfo("GRAN DEAL COZUMEL ¥")
                    .fullName("ANGEL CRUZ BETANZOS")
                    .supplier("EUROMUNDO")
                    .salesPerson("")
                    .branch("")
                    .city("")
                    .contactEmail("")
                    .contactPhoneNum("")
                    .build();
        }
        return ControllerUtils.getResponseSuccessOk(clientData);

    }


    @GetMapping("/test")
    public String readAPDF() {
        log.info("Reading PDF");
        try {
            File file = new File("/Users/said.guerrero/Downloads/magni.pdf");
            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper stripper = new PDFTextStripper();

            document.close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return "PDF read successfully";
    }
}
