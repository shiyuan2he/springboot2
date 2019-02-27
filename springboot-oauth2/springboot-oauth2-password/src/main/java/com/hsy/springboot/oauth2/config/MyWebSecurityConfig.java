package com.hsy.springboot.oauth2.config;
import com.hsy.springboot.oauth2.service.impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return new UserDetailServiceImpl();
    }

    /**
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        super.configure(authenticationManagerBuilder);
        /**
         * 用户从数据库中读取比对
         */
//        authenticationManagerBuilder.userDetailsService(baseUserDetailService());
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .requestMatchers().antMatchers("/oauth/**", "/login/**","/logout/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/oauth/**", "/login/**","/logout/**").permitAll()
                    .antMatchers("/oauth/**", "/login/**","/logout/**").authenticated()
//                .and().formLogin().permitAll()
                .and().csrf().disable()
                ;
    }
}
