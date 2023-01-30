package com.miniproject.threestarhotel.ui.controller;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class ApplicationController {

    private static String UPLOADED_FOLDER = "C:\\Users\\Handry\\Desktop\\" +
            "three-star-hotel\\src\\main\\resources\\static\\imgs\\";


    @RequestMapping(value = "/images/{photoName}", method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(HttpServletRequest request
            , @PathVariable("photoName") String filename) throws IOException {

        byte[] result = null;

        String[] tmp = request.getRequestURI().split("/");
        filename = tmp[tmp.length - 1];
        filename = filename.replace("%20", " ");

        try {
            File file = new File(UPLOADED_FOLDER.concat(filename));
            result = IOUtils.toByteArray(new FileInputStream(file));

        } catch (FileNotFoundException ex) {
        }

        return result;
    }
}
