package com.hsy.springboot.oauth2.all.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
    /*@Bean
    @Override
    protected UserDetailsService userDetailsService(){
        return super.userDetailsService();
    }*/

    /**
     * 配置两个用户， he、admin
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("$2a$10$iiDhNHOCUbl1wcYSiim9UOqOiB45LgKGLg84MrNDfqMOJ4/zO1Ady").roles("ADMIN", "USER")
                .and()
                .withUser("he").password("$2a$10$iiDhNHOCUbl1wcYSiim9UOqOiB45LgKGLg84MrNDfqMOJ4/zO1Ady").roles("USER")
                ;
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.antMatcher("/oauth/**").authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .and().csrf().disable()
                ;
    }
}
