package com.hsy.springboot.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
@Slf4j
public class Oauth2PasswordApplicationTest {

    @Test
    public void main() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("123");
        log.info(password);
    }
}