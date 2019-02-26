package com.hsy.springboot2.security.service.impl;
import com.hsy.springboot2.security.bean.User;
import com.hsy.springboot2.security.service.IUserService;
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
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserDetailsService{
    @Autowired
    PasswordEncoder passwordEncoder;
    private static List<Map<String, String>> userList = new ArrayList();
    static {
        Map<String, String> user = new HashMap<>();
        user.put("username", "admin");
        user.put("password", "123456");
        user.put("clientId", "client");
        user.put("secret", "secret");

        Map<String, String> user2 = new HashMap<>();
        user2.put("username", "he");
        user2.put("password", "123456");
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
                user.setAuthorities(AuthorityUtils.createAuthorityList("admin", "user"));
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
