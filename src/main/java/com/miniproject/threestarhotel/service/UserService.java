package com.miniproject.threestarhotel.service;

import com.miniproject.threestarhotel.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    List<UserDto> getUsers();
    UserDto getUserByUserId(String userId);
    List<UserDto> getTopUsers(String type);
    UserDto getUserByEmail(String email);
    List<UserDto> getUsers(int page, int limit);
    boolean checkUserByEmailForce(String email);
    void deleteUser(String userId);
    Page getUsersPage(Pageable pageable);
    Page getUsersPageSorted(Pageable pageable);
    List<UserDto> getUsersSorted();
}
