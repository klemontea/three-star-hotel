package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.UserPicture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UploadRepository extends CrudRepository<UserPicture, Long> {

    @Query(value = "select * from user_picture up where up.user_id = :userId",
            nativeQuery = true)
    List<UserPicture> getPictures(String userId);
}
