package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.entity.UserPicture;
import com.miniproject.threestarhotel.repository.UploadRepository;
import com.miniproject.threestarhotel.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService{

    @Autowired
    UploadRepository uploadRepository;

    @Autowired
    UserRepos userRepos;

    @Override
    public void saveUpload(String email, String path) {

        UserPicture userPicture = new UserPicture();
        UserEntity userEntity = userRepos.findByEmail(email);
        String userId = userEntity.getUserId();

        userPicture.setUserId(userId);
        userPicture.setPhotoPath(path);
        UserPicture storedPicture = uploadRepository.save(userPicture);
    }

    @Override
    public List<String> getPictures(String userId) {

        var result = new ArrayList<String>();
        List<UserPicture> userPictures = uploadRepository.getPictures(userId);

        for (UserPicture pic : userPictures) {

            String path = pic.getPhotoPath();
            result.add(path);
        }

        return result;
    }
}
