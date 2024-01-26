package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.domain.service.*;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadPDFServiceImpl implements ReadPDFService {

    @Autowired
    @Qualifier("magnichartersServiceImpl")
    private ProcessPDFService magnichartersService;

    @Autowired
    @Qualifier("euromundoServiceImpl")
    private ProcessPDFService euromundoService;

    @Autowired
    @Qualifier("newTrevelInnServiceImpl")
    private ProcessPDFService travelInService;

    @Override
    public Either<Error, ClientData> readPDF(List<String> files64) {
        log.info("Reading PDF");
        ClientData clientData = null;
        try {
            if (files64 == null || files64.isEmpty()) {
                return Either.left(Error.builder().message("No se recibieron archivos").build());
            }
//            if (files64.size() == 2) {
//                clientData = readTravelInPDF(files64);
//                if (clientData == null) {
//                    log.info("PDF is not TravelInn");
//                    return Either.left(Error.builder().message("PDF no valido").build());
//                }
//                return Either.right(clientData);
//            }
            byte[] bytes = Base64.getDecoder().decode(files64.get(0));
            PDDocument document = Loader.loadPDF(bytes);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            String textLower = text.toLowerCase();
            document.close();
            if (textLower.contains("magnicharters")) {
                clientData = magnichartersService.processPDF(text);

            } else if (textLower.contains("euromundo")) {
                clientData = euromundoService.processPDF(text);

            } else if (text.contains("VIAJES BUMERAN")) {
                clientData = travelInService.processPDF(text);

            } else {
                log.info("PDF is not Magnicharters");
                return Either.left(Error.builder().message("PDF no valido").build());
//                clientData = euromundoService.readEuromundoPDF(text);

            }
        } catch (Exception e) {
            return Either.left(Error.builder()
                    .message(e.getMessage())
                    .build());

        }
        return Either.right(clientData);

    }

    public ClientData readTravelInPDF(List<String> files64) throws IOException, ParseException {
        log.info("Reading TravelInn PDF");
        ClientData clientData = null;
        StringBuilder builder = new StringBuilder();
        for (String file : files64) {
            byte[] bytes = Base64.getDecoder().decode(file);
            PDDocument document = Loader.loadPDF(bytes);
            PDFTextStripper stripper = new PDFTextStripper();
            builder.append(stripper.getText(document));
            document.close();

        }
        if (builder.indexOf("Travelinn") != -1) {
            //  clientData = travelInService.readTravelinnPDF(builder.toString());

        }
        return clientData;

    }

}
