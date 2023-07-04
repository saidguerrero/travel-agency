package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.model.Orders;
import com.devas.travel.agency.domain.service.OrdersService;
import com.devas.travel.agency.domain.service.PDFService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFServiceImpl implements PDFService {

    private final OrdersService ordersService;

    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 36, Font.BOLD);

    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

    public byte[] generatePDF(ClientData clientData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            var orderOpt = ordersService.createOrder(clientData);

            if (orderOpt.isLeft()) {
                log.error("Error while generating PDF", orderOpt.getLeft());
                return baos.toByteArray();
            }
            var order = orderOpt.get();
            clientData.setCity(order.getCityByCityId().getDescription());
            clientData.setSupplier(order.getSupplierBySupplierId().getDescription());
            clientData.setBranch(order.getBranchByBranchId().getDescription());
            clientData.setSalesPerson(order.getSalesPerson().getFullName());
            Long id = order.getOrderId();
            Document document = new Document();
            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            document.open();
            addMetaData(document);
            addTitlePage(document, clientData, docWriter, id);
            document.close();

        } catch (Exception e) {
            log.error("Error while generating PDF", e);
        }
        return baos.toByteArray();

    }

    public byte[] generatePDFByOrderId(Long id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        var clientData = ordersService.getOrderById(id);
        if (clientData.isLeft()) {
            log.error("Error while generating PDF", clientData.getLeft());
            return baos.toByteArray();
        }
        try {
            Document document = new Document();
            PdfWriter docWriter = PdfWriter.getInstance(document, baos);
            document.open();
            addMetaData(document);
            addTitlePage(document, clientData.get(), docWriter, id);
            document.close();

        } catch (Exception e) {
            log.error("Error while generating PDF", e);
        }
        return baos.toByteArray();

    }

    private void addMetaData(Document document) {
        document.addTitle("Travel agency PDF");
        document.addSubject("PDF for viajes bumerang");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("said guerrero");
        document.addCreator("said guerrero");
    }

    private void addTitlePage(Document document, ClientData clientData, PdfWriter docWriter, Long id)
            throws DocumentException {
        Paragraph prefaceHeader1 = new Paragraph(Utils.dateToString(new Date(), FORMAT_DD_MM_YYYY), smallBold);
        prefaceHeader1.setAlignment(Element.ALIGN_RIGHT);

        Paragraph prefaceHeader2 = new Paragraph(Utils.leadZero(id), smallBold);
        prefaceHeader2.setAlignment(Element.ALIGN_RIGHT);
        addEmptyLine(prefaceHeader2, 1);

        Paragraph prefaceTitle = new Paragraph(clientData.getFullName(), catFont);
        prefaceTitle.setAlignment(Element.ALIGN_CENTER);

        Paragraph prefaceTitle2 = new Paragraph("$" + Utils.addingCommasToBigDecimal(clientData.getAmount()), catFont);
        prefaceTitle2.setAlignment(Element.ALIGN_CENTER);

        Paragraph prefaceTitle3 = new Paragraph(clientData.getTravelInfo(), catFont);
        prefaceTitle3.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(prefaceTitle3, 3);

        Paragraph prefaceBody = new Paragraph(clientData.getSupplier(), smallBold);
        prefaceBody.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody2 = new Paragraph("Localizador/Reservacion: " + clientData.getReservationNumber(), smallBold);
        prefaceBody2.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody3 = new Paragraph("Ciudad: " + clientData.getCity(), smallBold);
        prefaceBody3.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody4 = new Paragraph("Unidad Price Shoes: " + clientData.getBranch(), smallBold);
        prefaceBody4.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody5 = new Paragraph("Vendedor Price Shoes: " + clientData.getSalesPerson(), smallBold);
        prefaceBody5.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody6 = new Paragraph("Teléfono de Contacto: " + clientData.getContactPhoneNum(), smallBold);
        prefaceBody6.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody7 = new Paragraph("Email de Contacto: " + clientData.getContactEmail(), smallBold);
        prefaceBody7.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody8 = new Paragraph("Contacto de Emergencia " + clientData.getEmergencyContact(), smallBold);
        prefaceBody8.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceBody9 = new Paragraph("Teléfono de Emergencia " + clientData.getEmergencyContactPhone(), smallBold);
        prefaceBody9.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(prefaceBody9, 10);

        document.add(prefaceHeader1);
        document.add(prefaceHeader2);
        document.add(prefaceTitle);
        document.add(prefaceTitle2);
        document.add(prefaceTitle3);
        document.add(prefaceBody);
        document.add(prefaceBody2);
        document.add(prefaceBody3);
        document.add(prefaceBody4);
        document.add(prefaceBody5);
        document.add(prefaceBody6);
        document.add(prefaceBody7);
        document.add(prefaceBody8);
        document.add(prefaceBody9);

        PdfContentByte cb = docWriter.getDirectContent();
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode("viajesbumeran");
        barcode128.setCodeType(Barcode.CODE128);
        Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
        code128Image.scaleAbsolute(300, 150);
        code128Image.setAlignment(Element.ALIGN_CENTER);
        document.add(code128Image);
        // Start a new page
        document.newPage();
        addTermsAndConditions(document, docWriter);
    }

    private void addTermsAndConditions(Document document, PdfWriter docWriter) {
        // Load existing PDF
        PdfReader reader = null;
        try {
            File file = new File("/Users/said.guerrero/Documents/BUMERAN/terms-conditions.pdf");
            InputStream templateInputStream = new FileInputStream(file);
            reader = new PdfReader(templateInputStream);

        } catch (IOException e) {
            throw new RuntimeException("Error while loading PDF template", e);

        }
        PdfImportedPage page = docWriter.getImportedPage(reader, 1);

        // Copy first page of existing PDF into output PDF
        document.newPage();
        PdfContentByte cb = docWriter.getDirectContent();
        cb.addTemplate(page, 0, 0);

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
