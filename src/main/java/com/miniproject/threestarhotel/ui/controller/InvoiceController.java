package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.entity.Invoice;
import com.miniproject.threestarhotel.repository.InvoiceRepository;
import com.miniproject.threestarhotel.service.IExcelDataService;
import com.miniproject.threestarhotel.service.IFileUploaderService;
import com.miniproject.threestarhotel.service.UserService;
import com.miniproject.threestarhotel.ui.model.request.RegistrationRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    IFileUploaderService fileService;

    @Autowired
    IExcelDataService excelservice;

    @Autowired
    InvoiceRepository repo;

    @Autowired
    UserService userService;

    MultipartFile globalFile;

    @GetMapping("/excel")
    public String index() {
        return "uploadPage";
    }

    @PostMapping("/uploadExcel")
    public String uploadFile(@RequestParam("file") MultipartFile file
            , RedirectAttributes redirectAttributes) {

        globalFile = file;
        fileService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message",
                "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/excel";
    }

    @GetMapping("/saveData")
    public String saveExcelData(Model model) {

        List<Invoice> excelDataAsList = excelservice.getExcelDataAsList(globalFile);

        // Create users
        for (Invoice inv : excelDataAsList) {

            RegistrationRequest req = new RegistrationRequest();
            req.setFirstName(inv.getFirstName());
            req.setLastName(inv.getLastName());
            req.setIdentificationId(inv.getIdentificationID());
            req.setNationality(inv.getNationality());
            req.setEmail(inv.getEmail());
            req.setPassword(inv.getPassword());

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(req, userDto);
            userService.createUser(userDto);
        }

        int noOfRecords = excelservice.saveExcelData(excelDataAsList);
        model.addAttribute("noOfRecords",noOfRecords);
        return "success";
    }
}