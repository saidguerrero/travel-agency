package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.service.EuromundoService;
import com.devas.travel.agency.domain.service.MagnichartersService;
import com.devas.travel.agency.domain.service.ReadPDFService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadPDFServiceImpl implements ReadPDFService {

    private final MagnichartersService magnichartersService;

    private final EuromundoService euromundoService;

    @Override
    public Either<Error, ClientData> readPDF(List<String> files64) {
        log.info("Reading PDF");
        ClientData clientData = null;
        try {
            if (files64 == null || files64.isEmpty()) {
                return Either.left(Error.builder().message("No se recibieron archivos").build());
            }
            byte[] bytes = Base64.getDecoder().decode(files64.get(0));
            PDDocument document = Loader.loadPDF(bytes);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            if (text.contains("Magnicharters")) {
                clientData = magnichartersService.readMagnichartersPDF(text);

            } else if (text.contains("Euromundo")) {
                clientData = euromundoService.readEuromundoPDF(text);

            } else {
                log.info("PDF is not Magnicharters");
                return Either.left(Error.builder().message("PDF no valido").build());

            }
        } catch (Exception e) {
            return Either.left(Error.builder()
                    .message(e.getMessage())
                    .build());

        }
        return Either.right(clientData);

    }

}
