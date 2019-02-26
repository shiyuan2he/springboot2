package com.hsy.springboot2.security.bean;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
@ToString
public class User implements UserDetails {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户生效
     */
    private boolean accountNonExpired;
    /**
     * 账户锁定
     */
    private boolean accountNonLocked;
    /**
     * 凭证生效
     */
    private boolean credentialsNonExpired;
    /**
     * 激活状态
     */
    private boolean enabled;
    /**
     * 权限列表
     */
    private Collection<GrantedAuthority> authorities;
}

