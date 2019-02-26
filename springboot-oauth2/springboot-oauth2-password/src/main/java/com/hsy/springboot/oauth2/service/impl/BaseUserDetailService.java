package com.hsy.springboot.oauth2.service.impl;//package com.hsy.springboot.oauth2.server;

import com.hsy.springboot.oauth2.bean.UserDetailsBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
@Slf4j
public class BaseUserDetailService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("【密码授权】{}", username);
        UserDetailsBean userDetailsBean = null;
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("username is null");
        }
        if (true) {
            userDetailsBean = new UserDetailsBean();
            userDetailsBean.setUsername(username);
            userDetailsBean.setPassword(passwordEncoder.encode("123456"));
            userDetailsBean.setAuthorities(AuthorityUtils.createAuthorityList("read", "write"));
            userDetailsBean.setAccountNonExpired(true);
            userDetailsBean.setAccountNonLocked(true);
            userDetailsBean.setCredentialsNonExpired(true);
            userDetailsBean.setEnabled(true);
            log.info(userDetailsBean.toString());
        }
        return userDetailsBean;
    }
}