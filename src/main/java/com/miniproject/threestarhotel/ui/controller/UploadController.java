package com.miniproject.threestarhotel.ui.controller;

import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.service.UploadService;
import com.miniproject.threestarhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UploadController {

    @Autowired
    UploadService uploadService;

    @Autowired
    UserService userService;

    private static String UPLOADED_FOLDER = "C:\\Users\\Handry\\Desktop\\" +
            "three-star-hotel\\src\\main\\resources\\static\\imgs\\";


    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @GetMapping("/pictures/show/{userId}")
    public String showPics(@PathVariable String userId, Model model) {

        UserDto user = userService.getUserByUserId(userId);
        List<String> paths = uploadService.getPictures(userId);

        model.addAttribute("email", user.getEmail());
        model.addAttribute("userId", userId);
        model.addAttribute("pictures", paths);

        return "userPictures.html";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file
            , @RequestParam("email") String email
            , RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {

            redirectAttributes.addFlashAttribute("message",
                    "Please select a file to upload!");
            return "redirect:uploadStatus.html";
        }

        try {

            byte[] bytes = file.getBytes();
            String fullPath = UPLOADED_FOLDER + file.getOriginalFilename();
            Path path = Paths.get(fullPath);
            Files.write(path, bytes);

            String filename = file.getOriginalFilename();
            uploadService.saveUpload(email,filename);

            redirectAttributes.addFlashAttribute("message", "You successfully " +
                    "uploaded '" + filename + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {

        return "uploadStatus";
    }
}
