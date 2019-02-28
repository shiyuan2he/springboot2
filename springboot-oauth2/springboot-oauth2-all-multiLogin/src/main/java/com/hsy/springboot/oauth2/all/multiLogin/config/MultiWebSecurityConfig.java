package com.hsy.springboot.oauth2.all.multiLogin.config;
import com.hsy.springboot.oauth2.all.multiLogin.filter.AiLoginRedirectFilter;
import com.hsy.springboot.oauth2.all.multiLogin.filter.ParamTogetherFilter;
import com.hsy.springboot.oauth2.all.multiLogin.handler.MyLoginFailureHandler;
import com.hsy.springboot.oauth2.all.multiLogin.handler.MyLoginSuccessHandler;
import com.hsy.springboot.oauth2.all.multiLogin.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(2)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiWebSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        // 动态决定密码加密方式
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    /**
     * 通过自定义userDetailsService 来实现查询数据库，手机，二维码等多种验证方式
     */
    @Bean
    protected UserDetailsService userDetailsService(){
        //采用一个自定义的实现UserDetailsService接口的类
        return new UserServiceImpl();
    }
    /**
     * 配置两个用户， he、admin
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService())
        ;
    }
    /**
     * order中的数字越小，优先级越高
     */
    @Configuration
    @Order(1)
    public static class UsernameSecurityConfig extends WebSecurityConfigurerAdapter{
        @Autowired
        MyLoginFailureHandler myLoginFailureHandler;
        @Autowired
        MyLoginSuccessHandler myLoginSuccessHandler;
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
        @Bean
        AiLoginRedirectFilter aiLoginRedirectFilter() throws Exception {
            AiLoginRedirectFilter aiLoginRedirectFilter = new AiLoginRedirectFilter();
            aiLoginRedirectFilter.setAuthenticationManager(authenticationManagerBean());
            return aiLoginRedirectFilter;
        }
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            // 配置拦截范围
            httpSecurity
                    .addFilterBefore(aiLoginRedirectFilter() , UsernamePasswordAuthenticationFilter.class)
                    .requestMatchers().antMatchers("/admin/**", "/login/**", "/oauth/**", "/logout/**", "/view/**")
                    .and()
                        .authorizeRequests()
                            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                            .antMatchers("/admin/**", "/login/**", "/oauth/**", "/logout/**", "/view/**").permitAll()
                            .anyRequest().authenticated()
                    .and()
                        .formLogin()
                            .loginPage("/view/username")
                            .loginProcessingUrl("/login")
                            .permitAll()
                    .and()
                        .logout()
                            .logoutUrl("/logout/username")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .logoutSuccessHandler(new LogoutSuccessHandler() {
                                @Override
                                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                    httpServletResponse.sendRedirect("/view/username");
                                }
                            })
                    .and()
                        .csrf().disable()
            ;
        }
    }

    @Configuration
    @Order(2)
    public static class TelSecurityConfig extends WebSecurityConfigurerAdapter{

        /**
         * 配置两个用户， he、admin
         * @param authenticationManagerBuilder
         * @throws Exception
         */
        @Override
        protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.userDetailsService(userDetailsService()) ;
            super.configure(authenticationManagerBuilder);
        }
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .requestMatchers().antMatchers("/tel/**", "/loginTel/**", "/oauth/**", "/login/**", "/logout/**", "/view/**")
                    .and()
                    .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                     .antMatchers("/tel/**", "/loginTel/**", "/oauth/**", "/login/**", "/logout/**", "/view/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/view/tel")
                        .loginProcessingUrl("/loginTel")
                        .usernameParameter("tel")
                        .passwordParameter("code")
                        .permitAll()
                    .and()
                        .logout()
                            .logoutUrl("/logout/tel")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .logoutSuccessHandler(new LogoutSuccessHandler() {
                                @Override
                                public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                    httpServletResponse.sendRedirect("/view/tel");
                                }
                            })
                    .and()
                    .csrf().disable()
            ;
        }
    }
    /*@Configuration
    @Order(3)
    public static class WorkIdSecurityConfig extends WebSecurityConfigurerAdapter{
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        *//**
         * 通过自定义userDetailsService 来实现查询数据库，手机，二维码等多种验证方式
         *//*
        @Bean
        @Override
        protected UserDetailsService userDetailsService(){
            //采用一个自定义的实现UserDetailsService接口的类
            return new UserServiceImpl();
        }
        @Autowired
        MyLoginFailureHandler myLoginFailureHandler;
        @Autowired
        MyLoginSuccessHandler myLoginSuccessHandler;
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .antMatcher("/work/**").antMatcher("/loginWork/**")
                    .authorizeRequests()
//                    .antMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/view/work-id")
                        .loginProcessingUrl("/loginWork")
                        .usernameParameter("work-id")
                        .successHandler(myLoginSuccessHandler)
                        .failureHandler(myLoginFailureHandler)
                        .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout/work-id")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                            httpServletResponse.sendRedirect("/view/work-id");
                        }
                    })
                    .and()
                    .csrf().disable()
            ;
        }
    }*/
}
