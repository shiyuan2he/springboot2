package com.hsy.springboot.oauth2.all.service.impl;

import com.hsy.springboot.oauth2.all.bean.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Slf4j
//@Service("userService")
public class UserServiceImpl implements UserDetailsService{
    @Autowired
    PasswordEncoder passwordEncoder;
    private static List<Map<String, String>> userList = new ArrayList();
    static {
        Map<String, String> user = new HashMap<>();
        user.put("username", "admin");
        user.put("password", "123456");
        user.put("clientId", "client");
        user.put("authority", "admin, user");
        user.put("secret", "secret");

        Map<String, String> user2 = new HashMap<>();
        user2.put("username", "he");
        user2.put("password", "123456");
        user2.put("authority", "user");
        user2.put("clientId", "client");
        user2.put("secret", "secret");
        userList.add(user);
        userList.add(user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        User user = new User();
        for (Map<String, String> userMap : userList) {
            if(userMap.get("username").equals(username)){
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setAuthorities(AuthorityUtils.createAuthorityList(userMap.get("authority")));
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                user.setEnabled(true);
                break;
            }
        }
        return user;
    }
}
