package com.hsy.springboot.oauth2.all.service;//package com.hsy.springboot.oauth2.server;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
///**
// * Created with IntelliJ IDEA.
// * User: changjiang
// * Date: 2019/1/23
// * Time: 16:53
// * To change this template use File | Settings | File Templates.
// */
//public class SysUserAuthentication implements UserDetails {
//
//    /**
//     * ID号
//     */
//    private String uuid;
//    /**
//     * 用户名
//     */
//    private String username;
//    /**
//     * 密码
//     */
//    private String password;
//    /**
//     * 账户生效
//     */
//    private boolean accountNonExpired;
//    /**
//     * 账户锁定
//     */
//    private boolean accountNonLocked;
//    /**
//     * 凭证生效
//     */
//    private boolean credentialsNonExpired;
//    /**
//     * 激活状态
//     */
//    private boolean enabled;
//    /**
//     * 权限列表
//     */
//    private Collection<GrantedAuthority> authorities;
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return accountNonExpired;
//    }
//
//    public void setAccountNonExpired(boolean accountNonExpired) {
//        this.accountNonExpired = accountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    public void setAccountNonLocked(boolean accountNonLocked) {
//        this.accountNonLocked = accountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return credentialsNonExpired;
//    }
//
//    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
//        this.credentialsNonExpired = credentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    @Override
//    public Collection<GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    public void setAuthorities(Collection<GrantedAuthority> authorities) {
//        this.authorities = authorities;
//    }
//    @Override
//    public String toString() {
//        return "SysUserAuthentication{" +
//                "uuid='" + uuid + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", accountNonExpired=" + accountNonExpired +
//                ", accountNonLocked=" + accountNonLocked +
//                ", credentialsNonExpired=" + credentialsNonExpired +
//                ", enabled=" + enabled +
//                ", authorities=" + authorities +
//                '}';
//    }
//}
//
