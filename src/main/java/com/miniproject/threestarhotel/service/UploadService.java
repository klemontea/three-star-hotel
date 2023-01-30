package com.miniproject.threestarhotel.service;

import java.util.List;

public interface UploadService {

    void saveUpload(String email, String path);
    List<String> getPictures(String userId);
}
