package com.user.service;

import com.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
//    Dummy List
    List<User> list  = List.of(
            new User(1311L,"shabaz","7798861341"),
            new User(1312L,"faraz","1234567899"),
            new User(1313L,"shoeb","7798861343"),
            new User(1314L,"afshan","7798861345"),
            new User(1315L,"uzma","7798861346")
);

    @Override
    public User getUser(Long id) {
        return list.stream().filter(user -> user.getUserId().equals(id)).findAny().orElse(null);

    }
}
