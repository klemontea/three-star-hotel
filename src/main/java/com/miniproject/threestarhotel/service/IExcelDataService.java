package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.entity.Invoice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExcelDataService {

    List<Invoice> getExcelDataAsList(MultipartFile file);
    int saveExcelData(List<Invoice> invoices);
}
