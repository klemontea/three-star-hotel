package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.entity.Invoice;
import com.miniproject.threestarhotel.repository.InvoiceRepository;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelDataServiceImpl implements IExcelDataService {

//    @Value("${app.upload.file:${user.home}}")
//    public String EXCEL_FILE_PATH;

    private static String EXCEL_FILE_PATH = "C:\\Users\\Handry\\Desktop\\" +
            "three-star-hotel\\src\\main\\resources\\static\\";

    @Autowired
    InvoiceRepository repo;

    Workbook workbook;

    @Override
    public List<Invoice> getExcelDataAsList(MultipartFile file) {

        List<String> list = new ArrayList<>();

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // Create the Workbook
        try {
            workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH.concat(
                    file.getOriginalFilename())));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        // Retrieving the number of sheets in the Workbook
        System.out.println("-----Workbook has '" + workbook.getNumberOfSheets()
                + "' Sheets-----");

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println(sheet);

        // Getting number of columns in the Sheet
        int noOfColumns = sheet.getRow(0).getLastCellNum();
        System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

        // Using for-each loop to iterate over the rows and columns
        for (Row row : sheet) {
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                list.add(cellValue);
            }
        }

        for (String str : list) {
            System.out.println(str);
        }
        System.out.println("=======================================");

        // filling excel data and creating list as List<Invoice>
        List<Invoice> invList = createList(list, noOfColumns);

        for (Invoice inv : invList) {
            System.out.println(inv);
        }
        System.out.println("===================================");

        // Closing the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return invList;
    }

    private List<Invoice> createList(List<String> excelData, int noOfColumns) {

        ArrayList<Invoice> invList = new ArrayList<Invoice>();

        int i = noOfColumns;
        do {
            Invoice inv = new Invoice();

            inv.setEmail(excelData.get(i));
            inv.setPassword(excelData.get(i + 1));
            inv.setFirstName(excelData.get(i + 2));
            inv.setIdentificationID(excelData.get(i + 3));
            inv.setLastName(excelData.get(i + 4));
            inv.setNationality(excelData.get(i + 5));

            invList.add(inv);
            i = i + (noOfColumns);

        } while (i < excelData.size());
        return invList;
    }

    @Override
    public int saveExcelData(List<Invoice> invoices) {

        invoices = repo.saveAll(invoices);
        return invoices.size();
    }
}
