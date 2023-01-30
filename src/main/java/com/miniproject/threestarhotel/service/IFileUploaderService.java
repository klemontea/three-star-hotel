package com.miniproject.threestarhotel.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploaderService {

    public void uploadFile(MultipartFile file);
}
