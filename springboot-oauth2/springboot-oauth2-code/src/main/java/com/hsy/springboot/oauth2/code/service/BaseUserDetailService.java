package com.hsy.springboot.oauth2.code.service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: changjiang
 * Date: 2019/1/23
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class BaseUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("------{}", username);
        return new User(username, "secret", AuthorityUtils.createAuthorityList("USER"));
    }
//    @Autowired
//    private OauthInfoMapper oauthInfoMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("【密码授权】{}", username);
//        if (StringUtils.isBlank(username)) {
//            throw new UsernameNotFoundException("username is null");
//        }
//        /**
//         * TODO 此处需要查询用户信息表,并验证是否给与授权，将用户信息跟token绑定在一起
//         * @author heshiyuan
//         * 20/02/2019 16:58
//         */
//        SysUserAuthentication user = null;
//        /**
//         * 此种方式可以获取到用户信息
//         * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         */
//        if (true) {
//            /**
//             * 这里可以通过auth 获取 user 值
//             * 然后根据当前登录方式type 然后创建一个sysuserauthentication 重新设置 username 和 password
//             * 比如使用手机验证码登录的， username就是手机号 password就是6位的验证码{noop}000000
//             * 所谓的角色，只是增加ROLE_前缀
//             */
//            List<GrantedAuthority> list = AuthorityUtils.createAuthorityList(Oauth2RolesEnum.CBC.getCode());
//            user = new SysUserAuthentication();
//            user.setUsername(username);
//            user.setPassword("{noop}123456");
//            user.setAuthorities(list);
//            user.setAccountNonExpired(true);
//            user.setAccountNonLocked(true);
//            user.setCredentialsNonExpired(true);
//            user.setEnabled(true);
//            log.info(user.toString());
//        }
//        return user;
//    }
}