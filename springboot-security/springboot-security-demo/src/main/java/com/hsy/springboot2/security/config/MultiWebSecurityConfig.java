package com.hsy.springboot2.security.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class MultiWebSecurityConfig{

    @Bean
    PasswordEncoder passwordEncoder(){
        // 密码不加密模式
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置两个用户， he、admin
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("123").roles("ADMIN", "USER")
                .and()
                .withUser("he").password("123").roles("USER")
                ;
    }

    /**
     * order中的数字越小，优先级越高
     */
    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .csrf().disable()
            ;

        }
    }
    public static class OtherSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                        .formLogin()
                            .loginProcessingUrl("/login")
                            .permitAll()
                    .and()
                    .csrf().disable()
            ;

        }
    }
}
