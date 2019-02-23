package com.hsy.springboot2.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        // 密码不加密模式
//        return NoOpPasswordEncoder.getInstance();
        /**
         * BCryptPasswordEncoder使用BCrypt强哈希函数  10表示十次密码迭代
         */
        return new BCryptPasswordEncoder(10);
    }

    /**
     * 配置两个用户， he、admin
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("123").roles("ADMIN", "USER")
                .and()
                .withUser("he").password("123").roles("USER")
                ;
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')")
                    .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    // 其他任意请求需要授权
                    .anyRequest().authenticated()
            .and()
                // 开启表单登陆
                .formLogin()
                    .loginPage("/view/username")
                    .loginProcessingUrl("/login")
                    .usernameParameter("name")
                    .passwordParameter("passwd")
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
                            httpServletResponse.setContentType("application/json;charset=utf-8");
                            PrintWriter printWriter = httpServletResponse.getWriter();
                            httpServletResponse.setStatus(401);
                            Map<String, Object> map = new HashMap<>();
                            map.put("status", 401);
                            map.put("msg", "登陆失败");
                            ObjectMapper objectMapper = new ObjectMapper();
                            printWriter.write(objectMapper.writeValueAsString(map));
                            printWriter.flush();
                            printWriter.close();
                        }
                    })
                    // 和登陆相关的所有接口放行
                    .permitAll()
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .addLogoutHandler(new LogoutHandler() {
                        @Override
                        public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

                        }
                    })
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
