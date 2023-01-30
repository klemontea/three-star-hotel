package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.UserDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<UserDto> listUsers;

    public ExcelExporter(List<UserDto> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {

        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell (row, 0, "userID", style);
        createCell(row, 1, "firstName", style);
        createCell(row, 2, "lastName", style);
        createCell(row, 3, "identificationID", style);
        createCell(row, 4, "email", style);
        createCell(row, 5, "nationality", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines() {

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (UserDto user : listUsers) {

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getUserId(), style);
            createCell(row, columnCount++, user.getFirstName(), style);
            createCell(row, columnCount++, user.getLastName(), style);
            createCell(row, columnCount++, user.getIdentificationId(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getNationality(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {

        final String FILE_NAME = "C:\\Users\\Handry\\Downloads\\test.xlsx";

        writeHeaderLine();
        writeDataLines();

//        ServletOutputStream outputStream = response.getOutputStream();
        FileOutputStream fileOutputStream  = new FileOutputStream(FILE_NAME);
        workbook.write(fileOutputStream);
        workbook.close();

        fileOutputStream.close();
    }
}
