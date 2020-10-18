package org.itstep.model.service;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    public static Map<String, User> users = new HashMap<>();

    public UserService() {
        users.put("Andy", User.builder()
                .id(1l)
        .username("Andy")
        .usernameukr("Андрій")
        .email("hjghgg@kjhk.ua")
        .password("123")
        .active(true)
        .roles(Collections.singleton(Role.USER))
        .build());

        users.put("John", User.builder()
                .id(2l)
                .username("John")
                .usernameukr("Іван")
                .email("hjghgg@kjhk.ua")
                .password("1234")
                .active(true)
                .roles(Collections.singleton(Role.TEACHER))
                .build());

        users.put("Nick", User.builder()
                .id(3l)
                .username("Nick")
                .usernameukr("Микола")
                .email("hjghgg@kjhk.ua")
                .password("12")
                .active(true)
                .roles(Collections.singleton(Role.ADMIN))
                .build());

    }

}
