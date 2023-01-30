package com.miniproject.threestarhotel.ui.controller.datatable;

import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.ui.model.datatable.Page;
import com.miniproject.threestarhotel.ui.model.datatable.PagingRequest;
import com.miniproject.threestarhotel.service.datatable.UserDTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("datatable")
public class UserDTRestController {

    @Autowired
    private UserDTService userDTService;

    @PostMapping("/users")
    public Page<UserDto> list(@RequestBody PagingRequest pagingRequest) {
        return userDTService.getUsers(pagingRequest);
    }
}
