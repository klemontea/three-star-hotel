package com.miniproject.threestarhotel.service.datatable;

import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.repository.UserRepos;
import com.miniproject.threestarhotel.ui.model.datatable.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDTService {

    @Autowired
    UserRepos userRepos;

    private static final Comparator<UserDto> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private List<String> toStringList(UserDto userDto) {
        return Arrays.asList(userDto.getUserId(), userDto.getIdentificationId(),
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
                userDto.getNationality(), userDto.getRegisterDate().toString());
    }

    public Page<UserDto> getUsers(PagingRequest pagingRequest) {

        try {
            List<UserEntity> userEntities = (List<UserEntity>) userRepos.findAll();
            List<UserDto> userDtos = userEntityToDto(userEntities);

            return getPage(userDtos, pagingRequest);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new com.miniproject.threestarhotel.ui.model.datatable.Page<>();
    }

    protected List<UserDto> userEntityToDto(List<UserEntity> entities) {

        var result = new ArrayList<UserDto>();

        for (UserEntity entity : entities) {
            var dto = new UserDto();
            dto.setId(entity.getId());
            dto.setUserId(entity.getUserId());
            dto.setIdentificationId(entity.getIdentificationId());
            dto.setFirstName(entity.getFirstName());
            dto.setLastName(entity.getLastName());
            dto.setEmail(entity.getEmail());
            dto.setNationality(entity.getNationality());
            dto.setRegisterDate(entity.getRegisterDate());

            result.add(dto);
        }

        return result;
    }

    private Page<UserDto> getPage(List<UserDto> userDtos, PagingRequest pagingRequest) {
        List<UserDto> filtered = userDtos.stream()
                .sorted(sortUserDtos(pagingRequest))
                .filter(filterUserDtos(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = userDtos.stream()
                .filter(filterUserDtos(pagingRequest))
                .count();

        Page<UserDto> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<UserDto> filterUserDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return userDto -> true;
        }

        String value = pagingRequest.getSearch().getValue();

        return userDto -> userDto.getFirstName().toLowerCase().contains(value)
                || userDto.getLastName().toLowerCase().contains(value)
                || userDto.getNationality().toLowerCase().contains(value)
                || userDto.getIdentificationId().contains(value);
    }

    private Comparator<UserDto> sortUserDtos(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);

            Comparator<UserDto> comparator = UserComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }
            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

}
