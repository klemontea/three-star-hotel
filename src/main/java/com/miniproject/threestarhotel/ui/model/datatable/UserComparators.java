package com.miniproject.threestarhotel.ui.model.datatable;

import com.miniproject.threestarhotel.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class UserComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<UserDto>> map = new HashMap<>();

    static {
        map.put(new Key("firstName", Direction.asc), Comparator.comparing(UserDto::getFirstName));
        map.put(new Key("firstName", Direction.desc), Comparator.comparing(UserDto::getFirstName).reversed());

        map.put(new Key("lastName", Direction.asc), Comparator.comparing(UserDto::getLastName));
        map.put(new Key("lastName", Direction.desc), Comparator.comparing(UserDto::getLastName).reversed());

        map.put(new Key("registerDate", Direction.asc), Comparator.comparing(UserDto::getRegisterDate));
        map.put(new Key("registerDate", Direction.desc), Comparator.comparing(UserDto::getRegisterDate).reversed());

        map.put(new Key("email", Direction.asc), Comparator.comparing(UserDto::getEmail));
        map.put(new Key("email", Direction.desc), Comparator.comparing(UserDto::getEmail).reversed());

        map.put(new Key("nationality", Direction.asc), Comparator.comparing(UserDto::getNationality));
        map.put(new Key("nationality", Direction.desc), Comparator.comparing(UserDto::getNationality).reversed());

        map.put(new Key("identificationId", Direction.asc), Comparator.comparing(UserDto::getIdentificationId));
        map.put(new Key("identificationId", Direction.desc), Comparator.comparing(UserDto::getIdentificationId).reversed());
    }

    public static Comparator<UserDto> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private UserComparators() {
    }

}
