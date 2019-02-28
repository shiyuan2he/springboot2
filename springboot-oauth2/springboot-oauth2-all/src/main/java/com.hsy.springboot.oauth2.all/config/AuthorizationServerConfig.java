package com.hsy.springboot.oauth2.all.config;

import com.hsy.springboot.oauth2.all.service.impl.ClientDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userService;
//    @Autowired
//    ClientDetailsService clientDetailServiceImpl;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        // 根据数据库动态注册
        clients.withClientDetails(new ClientDetailServiceImpl());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception{
        endpointsConfigurer.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
//                .userDetailsService(userService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                ;
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer) throws Exception{
        // 允许表单验证
        securityConfigurer.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }
}
