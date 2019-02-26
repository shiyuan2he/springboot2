package com.hsy.springboot2.security.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsy.springboot2.security.handler.MyLoginFailureHandler;
import com.hsy.springboot2.security.handler.MyLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiWebSecurityConfig{
    @Autowired
    UserDetailsService userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        // 动态决定密码加密方式
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 配置两个用户， he、admin
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userService)
                ;
    }

    /**
     * order中的数字越小，优先级越高
     */
    @Configuration
    @Order(1)
    public static class UsernameSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .antMatcher("/admin/**").antMatcher("/login/**")
                    .authorizeRequests()
//                    .antMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                        .formLogin()
                            .loginPage("/view/username")
                            .loginProcessingUrl("/login")
                            .successHandler(new AuthenticationSuccessHandler() {
                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                    Object principal = authentication.getPrincipal();
                                    httpServletResponse.setContentType("application/json;charset=utf-8");
                                    PrintWriter printWriter = httpServletResponse.getWriter();
                                    httpServletResponse.setStatus(200);
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("status", 200);
                                    map.put("msg", principal);
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    printWriter.write(objectMapper.writeValueAsString(map));
                                    printWriter.flush();
                                    printWriter.close();
                                }
                            })
                            .failureHandler(new AuthenticationFailureHandler() {
                                @Override
                                public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                    System.out.println("登陆失败");
                                    httpServletResponse.sendRedirect("/view/username");
                                }
                            })
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
        @Autowired
        MyLoginFailureHandler myLoginFailureHandler;
        @Autowired
        MyLoginSuccessHandler myLoginSuccessHandler;
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .antMatcher("/tel/**").antMatcher("/loginTel/**")
                    .authorizeRequests()
//                    .antMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/view/tel")
                        .loginProcessingUrl("/loginTel")
                        .usernameParameter("tel")
                        .passwordParameter("code")
                        .successHandler(myLoginSuccessHandler)
                        .failureHandler(myLoginFailureHandler)
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
    @Configuration
    @Order(3)
    public static class WorkIdSecurityConfig extends WebSecurityConfigurerAdapter{
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
    }
}
