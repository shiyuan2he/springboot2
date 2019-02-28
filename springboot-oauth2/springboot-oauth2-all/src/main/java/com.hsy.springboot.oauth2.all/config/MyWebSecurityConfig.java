package com.hsy.springboot.oauth2.all.config;
import com.hsy.springboot.oauth2.all.filter.AiLoginRedirectFilter;
import com.hsy.springboot.oauth2.all.service.impl.UserServiceImpl;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Order(2)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
//    @Autowired
//    UserDetailsService userService;
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
    /**
     * 通过自定义userDetailsService 来实现查询数据库，手机，二维码等多种验证方式
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        //采用一个自定义的实现UserDetailsService接口的类
        return new UserServiceImpl();
    }


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
                .addFilterBefore(aiLoginRedirectFilter(), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**", "/view/**")
                .and()
                .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .antMatchers("/oauth/**", "/login/**", "/logout/**" , "/view/**").permitAll()
                .and()
                    .formLogin()
                        .loginPage("/view/username")
                        .loginProcessingUrl("/login")
                    .permitAll()
                .and().csrf().disable()
                ;
    }
}
