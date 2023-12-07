package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.ClientData;
import com.devas.travel.agency.domain.service.OrdersService;
import com.devas.travel.agency.domain.service.PDFService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class PDFServiceImpl implements PDFService {

    private final OrdersService ordersService;

    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 36, Font.BOLD);
    private static final Font catFontTiny = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);

    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String FORMAT_HH_MM = "HH:mm";

    public static final String PRICE_SHOES_CODE = "1140301";

    public byte[] generatePDF(ClientData clientData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            var orderOpt = ordersService.createOrder(clientData);

            if (orderOpt.isLeft()) {
                log.error("Error al generar el PDF");
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
            log.error("Error: {}", e);
        }
        return baos.toByteArray();

    }

    public byte[] generatePDFByOrderId(Long id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        var clientData = ordersService.getOrderById(id);
        if (clientData.isLeft()) {
            log.info("No se encontro el PDF para la orden");
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
            log.error("Error al encontrar el PDF por id orden : {}", e);
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
            throws DocumentException, IOException {
//        String logoPath = "static/logoVB.png";
//        ClassLoader classLoader = PDFServiceImpl.class.getClassLoader();
//        InputStream templateInputStream = classLoader.getResourceAsStream(logoPath);
//        Image templateImage = Image.getInstance(templateInputStream.readAllBytes());
//        templateImage.setAbsolutePosition(0, 700);
//        templateImage.scaleAbsolute(500, 100);
//        templateImage.setAlignment(Element.ALIGN_LEFT);
//        document.add(templateImage);

        Paragraph title = new Paragraph("Orden de Pago                     " + Utils.leadZero(id, 4), catFont);
        title.setAlignment(Element.ALIGN_LEFT);

        Paragraph prefaceHeader1 = new Paragraph(Utils.dateToString(LocalDateTime.now(), FORMAT_DD_MM_YYYY), smallBold);
        prefaceHeader1.setAlignment(Element.ALIGN_RIGHT);

        addEmptyLine(prefaceHeader1, 1);

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
//        addEmptyLine(prefaceBody9, 1);

        Paragraph prefaceBody10 = new Paragraph(Utils.dateToString(LocalDateTime.now(), FORMAT_HH_MM), catFont);
        prefaceBody10.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(prefaceHeader1);
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
        document.add(prefaceBody10);

        document.add(getBarcodeImage(docWriter, clientData.getAmount()));

        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph signLines = new Paragraph("_______________________");
        signLines.add(new Chunk(glue));
        signLines.add("____________________");

        document.add(signLines);

        Chunk glue1 = new Chunk(new VerticalPositionMark());
        Paragraph signLabel = new Paragraph("Firma conformidad de cliente");
        signLabel.add(new Chunk(glue1));
        signLabel.add("Sello de pago en la caja");
        document.add(signLabel);

        //tabla
//        PdfPTable table = new PdfPTable(2);
//        table.setWidthPercentage(100);
//        table.addCell(getBarcodeImageMini(docWriter, clientData.getReservationNumber()));
//
//        table.addCell(getCell("Hora de vencimiento: " + add1HourToDate(date), PdfPCell.ALIGN_RIGHT));
//        document.add(table);

        document.add(getBarcodeImageMini(docWriter, clientData.getReservationNumber()));

        Paragraph horaVencimiento = new Paragraph("Hora de vencimiento: " + add1HourToDate(LocalDateTime.now()), catFontTiny);
        horaVencimiento.setAlignment(Element.ALIGN_RIGHT);
        document.add(horaVencimiento);
        // Start a new page
        document.newPage();
        addTermsAndConditions(document, docWriter);
    }

//    public String add1HourToDate(Date date) {
//        LocalDateTime nextTime = localDateTime.plusHours(1L);
//        return Utils.dateToString(calendar.getTime(), FORMAT_HH_MM);
//    }

    public String add1HourToDate(LocalDateTime localDateTime) {
        LocalDateTime nextTime = localDateTime.plusHours(1L);
        return Utils.dateToString(nextTime, FORMAT_HH_MM);
    }

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    private Image getBarcodeImage(PdfWriter docWriter, BigDecimal amount) {
        PdfContentByte cb = docWriter.getDirectContent();
        Barcode128 barcode128 = getBarcode(amount);
        Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
        code128Image.scaleAbsolute(150, 75);
        code128Image.setAlignment(Element.ALIGN_CENTER);
        return code128Image;
    }

    private Rectangle getRectangleForClientSign(PdfWriter writer) throws DocumentException {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(0, 780, 494, 820);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBackgroundColor(BaseColor.GRAY);
        rect.setBorderColor(BaseColor.GREEN);
        canvas.rectangle(rect);

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(rect);
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        ct.addElement(new Paragraph("Your Text Goes here!! ", catFont));
        ct.go();
        return rect;

    }

    private Image getBarcodeImageMini(PdfWriter docWriter, String localizador) {
        PdfContentByte cb = docWriter.getDirectContent();
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(localizador);
        barcode128.setCodeType(Barcode.CODE128);
        Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
        code128Image.scaleAbsolute(100, 20);
        code128Image.setAlignment(Element.ALIGN_LEFT);
        return code128Image;
    }

    private Barcode128 getBarcode(BigDecimal amount) {
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(getBarcodeValue(amount));
        barcode128.setCodeType(Barcode.CODE128);
        return barcode128;
    }

    private String getBarcodeValue(BigDecimal amount) {
        var amountRound = Utils.amountRoundUp(amount);
        return PRICE_SHOES_CODE + "$" + Utils.leadZero (Long.parseLong(amountRound), 7);

    }

    private void addTermsAndConditions(Document document, PdfWriter docWriter) {
        // Load existing PDF
        PdfReader reader;
        try {
            // Ruta relativa del archivo PDF en resources/static
            String pdfPath = "static/terms-conditions.pdf";
            ClassLoader classLoader = PDFServiceImpl.class.getClassLoader();
            InputStream templateInputStream = classLoader.getResourceAsStream(pdfPath);

            if (templateInputStream != null) {
                reader = new PdfReader(templateInputStream);
            } else {
                throw new IllegalArgumentException("Error while loading PDF template");
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Error while loading PDF template", e);

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
