package com.devas.travel.agency.domain.service.impl;


import com.devas.travel.agency.domain.service.ExcelService;
import com.devas.travel.agency.domain.service.OrdersService;
import com.devas.travel.agency.infrastructure.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.devas.travel.agency.infrastructure.constants.Constants.FORMATDDMMYYYY;
import static com.devas.travel.agency.infrastructure.constants.Constants.FORMAT_HH_MM_ss;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final OrdersService ordersService;

    public static final String[] EXCEL_COLUMNS = {
            "ID DE VENTA",
            "FECHA INICIAL DE LA VENTA",
            "MODULO",
            "VENDEDOR",
            "NOMBRE DEL PASAJERO PRINCIPAL",
            "TELEFONO DEL PASAJERO PRINCIPAL",
            "E-MAIL DEL PASAJERO PRINCIPAL",
            "NOMBRE DEL CONTACTO DE EMERGENCIA",
            "TELEFONO DEL CONTACTO DE EMERGENCIA",
            "SERVICIO ADQUIRIDO",
            "DESTINO",
            "HOTEL",
            "BROKER",
            "LOCALIZADOR",
            "NUMERO DE PASAJEROS",
            "FECHA INICIO DEL SERVICIO",
            "FECHA TERMINO DEL SERVICIO",
            "PRECIO DEL SERVICIO",
            "MONEDA",
            "TIPO DE CAMBIO",
            "PRECIO DEL SERVICIO EN MXN",
            "CARGO POR SERVICIO",
            "COSTO TOTAL AL CLIENTE",
            "TIPO DE PAGO DEL CLIENTE",
            "FECHA DE PAGO DEL CLIENTE",
            "FORMA DE PAGO DEL CLIENTE",
            "HORA DE PAGO EN CAJAS PRICE",
            "ESTATUS DE PAGO DEL CLIENTE",
            "COMISION DE LA VENTA",
            "% QUE ARROJA LA VENTA"
    };

    @Override
    public byte[] createExcelFile(int userId, int roleId) {
        log.info("createExcelFile() - {}");
        //sayidguerrero83
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<String[]> data = new ArrayList<>();
        try {
            var orders = ordersService.getExcelData(userId, roleId);
//            log.info(orders.toString());
//            ordersService.getExcelData(userId, roleId)
            orders.forEach(order -> {
                String[] info = new String[EXCEL_COLUMNS.length];
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat hourDF = new SimpleDateFormat("HH:mm:ss");

                info[0] = order.getSaleId();

                info[1] = Utils.localDateTimeToString(order.getOrderDate(), FORMATDDMMYYYY);
                info[2] = order.getBranchByBranchId().getDescription();
                info[3] = order.getSalesPerson().getFullName();
                info[4] = order.getFullName();
                info[5] = order.getContactPhoneNum();
                info[6] = order.getContactEmail();
                info[7] = order.getEmergencyContact();
                info[8] = order.getEmergencyContactPhone();
                var typeService = "";
                if(order.getTypeServiceById() != null && StringUtils.isNoneEmpty(order.getTypeServiceById().getDescription())){
                    typeService = order.getTypeServiceById().getDescription();
                }
                info[9] = typeService ;

                info[10] = order.getTravelInfo();
                info[11] = order.getHotel();
                info[12] = order.getSupplierBySupplierId().getDescription();
                info[13] = order.getReservationNumber();
                info[14] = order.getNumberOfPassengers();

                info[15] = Utils.localDateToString(order.getStartDate(), FORMATDDMMYYYY);
                info[16] = Utils.localDateToString(order.getEndDate(), FORMATDDMMYYYY);
                info[17] = order.getAmount().toString();
                info[18] = order.getExchange();

                info[19] = "1.00";
                info[20] = order.getAmountPesos().toString();
                BigDecimal commission = order.getAmountWCommission() != null ? order.getAmountWCommission().subtract(order.getAmount()) : new BigDecimal(BigInteger.ZERO);
                info[21] = commission.toString();

                info[22] = order.getAmountWCommission() != null ? order.getAmountWCommission().toString() : "0";

                var paymentType = "";
                if(order.getPaymentTypeById() != null && StringUtils.isNoneEmpty(order.getPaymentTypeById().getDescription())){
                    paymentType = order.getPaymentTypeById().getDescription();
                }
                info[23] = paymentType;
                info[24] = Utils.localDateTimeToString(order.getOrderDate(), FORMATDDMMYYYY);

                var paymentMethod = "";
                if(order.getPaymentMethodById() != null && StringUtils.isNoneEmpty(order.getPaymentMethodById().getDescription())){
                    paymentMethod = order.getPaymentTypeById().getDescription();
                }
                info[25] = paymentMethod;
                info[26] = Utils.localDateTimeToString(order.getOrderDate(), FORMAT_HH_MM_ss);

                //TODO: revisar estos ultimos valores
                info[27] = "";
                info[28] = "";
                info[29] = "";
                data.add(info);
            });
            String[] columns = EXCEL_COLUMNS;
            String title = "Ventas de modulos";
            baos = generateXLS(data, columns, title);

        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }
        return baos.toByteArray();

    }

    public ByteArrayOutputStream generateXLS(List<String[]> data, String[] columnas, String titulo) throws IOException {
        log.info("generateXLS() - {}", titulo);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            // Crea el workbook

            // Crea la hoja de excel para los estudios
            HSSFSheet sheet = workbook.createSheet(titulo);

            // Crea la fila para el encabezado, definir estilo y crear el encabezado
            HSSFRow rowHeader = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            HSSFFont font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);

            for (int index = 0; index < columnas.length; index++) {
                HSSFCell headerCell = rowHeader.createCell(index);
                headerCell.setCellValue(columnas[index]);
                headerCell.setCellStyle(headerStyle);
            }

            // Se escriben la información
            for (int rowIndex = 1; rowIndex < data.size(); rowIndex++) {
                HSSFRow row = sheet.createRow(rowIndex);
                String[] fila = data.get(rowIndex - 1);
                for (int colIndex = 0; colIndex < columnas.length; colIndex++) {
                    HSSFCell cell = row.createCell(colIndex);
                    cell.setCellValue((fila[colIndex] == null) ? "" : fila[colIndex]);
                }
            }

            // Serializar el workbook a Base64

            workbook.write(outputStream);


        } catch (Exception e) {
            log.error("ocurrio un error al intentar descargar el excel: {}", e.getMessage());

        }
        return outputStream;

    }
}
