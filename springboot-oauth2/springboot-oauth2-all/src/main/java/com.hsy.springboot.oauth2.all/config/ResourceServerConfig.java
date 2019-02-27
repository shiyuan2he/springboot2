package com.hsy.springboot.oauth2.all.config;

import com.hsy.springboot.oauth2.all.filter.JsonToUrlEncodedAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    JsonToUrlEncodedAuthenticationFilter jsonToUrlEncodedAuthenticationFilter;
    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) throws Exception{
        configurer.resourceId("resourceId")
                .stateless(true)
                ;
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .addFilterBefore(jsonToUrlEncodedAuthenticationFilter, ChannelProcessingFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .antMatchers("/openapi/**").hasRole("user")
                .anyRequest().authenticated()
                ;
    }
}
