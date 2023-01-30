package com.miniproject.threestarhotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.threestarhotel.dto.UserDto;
import com.miniproject.threestarhotel.entity.UserEntity;
import com.miniproject.threestarhotel.exception.ApplicationException;
import com.miniproject.threestarhotel.repository.UserRepos;
import com.miniproject.threestarhotel.security.CustomUserDetails;
import com.miniproject.threestarhotel.security.Utils;
import com.miniproject.threestarhotel.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepos userRepos;

    @Autowired
    Utils utils;

    @Override
    public CustomUserDetails loadUserByUsername(String email) {

        Supplier<UsernameNotFoundException> s = () ->
                new UsernameNotFoundException("Problem during authentication!");

        UserEntity u = userRepos.findByEmail(email);

        return new CustomUserDetails(u);
    }

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId();
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptPassword(user.getPassword());
        userEntity.setRegisterDate(LocalDate.now());

        UserEntity storedUserDetails = userRepos.save(userEntity);
        UserDto result = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, result);

        return result;
    }

    @Override
    public List<UserDto> getUsers() {

        List<UserDto> result = new ArrayList<>();
        List<UserEntity> users = (List<UserEntity>) userRepos.findAll();

        for (UserEntity user : users) {

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            result.add(userDto);
        }

        return result;
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {

        List<UserDto> result = new ArrayList<>();

        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepos.findAll(pageable);
        List<UserEntity> users =usersPage.getContent();

        for (UserEntity user : users) {

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            result.add(userDto);
        }

        return result;
    }

    @Override
    public List<UserDto> getUsersSorted() {

        List<UserDto> result = new ArrayList<>();
        List<UserEntity> users = (List<UserEntity>) userRepos.findAllSorted();

        for (UserEntity user : users) {

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            result.add(userDto);
        }

        return result;
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserDto result = new UserDto();
        UserEntity userEntity = userRepos.findByUserId(userId);

        BeanUtils.copyProperties(userEntity, result);
        return result;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        UserEntity userEntity = userRepos.findByEmail(email);

        if (userEntity != null) throw new ApplicationException(ErrorMessages
                .EMAIL_ALREADY_EXISTS.getErrorMessage());

        UserDto result = null;
        ObjectMapper qwe = new ObjectMapper();

        if(null!=userEntity){
            BeanUtils.copyProperties(userEntity, result);
        }

        return result;
    }

    @Override
    public boolean checkUserByEmailForce(String email) {

        UserEntity userEntity = userRepos.findByEmail(email);
        return (userEntity == null) ? false : true;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepos.findByUserId(userId);

        if (userEntity == null) throw new ApplicationException(ErrorMessages.
                USER_NOT_FOUND.getErrorMessage());

        userRepos.delete(userEntity);
    }

    @Override
    public Page getUsersPage(Pageable pageable) {
        Page<UserEntity> usersPage = userRepos.findAll(pageable);
        return usersPage;
    }

    @Override
    public Page getUsersPageSorted(Pageable pageable) {
        Page<UserEntity> usersPage = userRepos.findAllSortedExtend(pageable);

        return usersPage;
    }

    @Override
    public List<UserDto> getTopUsers(String type) {

        var result = new ArrayList<UserDto>();
        List<UserEntity> users = userRepos.findTopUsers(type);
        List<UserEntity> filteredUsers = new ArrayList<>();

        Map<UserEntity, Integer> bowl = new HashMap<UserEntity, Integer>();
        for (UserEntity user : users) {

            Integer j = bowl.get(user);
            bowl.put(user, (j == null) ? 1 : j + 1);
        }

        // Sort from low to high
        Map<UserEntity, Integer> sortMap = sortByValue(
                (HashMap<UserEntity, Integer>) bowl);
        ArrayList list = new ArrayList(sortMap.keySet());

        // Take top 3
        for (int i = list.size(); i > list.size() - 3; i--) {

            UserEntity key = (UserEntity) list.get(i - 1);
            filteredUsers.add(key);
        }

        for (UserEntity userEntity : filteredUsers) {

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            result.add(userDto);
        }

        return result;
    }

    public static HashMap<UserEntity, Integer> sortByValue(HashMap<UserEntity,
            Integer> maps)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<UserEntity, Integer> > list =
                new LinkedList<Map.Entry<UserEntity, Integer> >(maps.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<UserEntity, Integer> >() {
            public int compare(Map.Entry<UserEntity, Integer> o1,
                               Map.Entry<UserEntity, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<UserEntity, Integer> temp = new LinkedHashMap<UserEntity, Integer>();
        for (Map.Entry<UserEntity, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}