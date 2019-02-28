package com.hsy.springboot.oauth2.all.filter;

import com.hsy.springboot.oauth2.all.common.CommonConstant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AiLoginRedirectFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_LOGIN_TYPE_KEY = "login_type";
    @Getter
    private String usernameParameter = "username";
    @Getter
    private String passwordParameter = "password";
    @Getter
    private String loginTypeParameter = "login_type";
    @Setter
    private boolean postOnly = true;
    public AiLoginRedirectFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }
    protected AiLoginRedirectFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected AiLoginRedirectFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        } else {
            String username = this.obtainUsername(httpServletRequest);
            String password = this.obtainPassword(httpServletRequest);
            String loginType = this.obtainLoginType(httpServletRequest);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }
            if (loginType == null) {
                loginType = "";
            }

            username = username.trim();
            StringBuilder newUserName = new StringBuilder();
            if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(loginType)){
                newUserName.append(username)
                        .append(CommonConstant.USERNAME_LOGINTYPE_SPLIT)
                        .append(loginType);
            }
            /*AiLoginRedirectToken authRequest =
                    new AiLoginRedirectToken(newUserName.toString(), password, loginType);*/
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(newUserName.toString(), password);
            this.setDetails(httpServletRequest, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private String obtainLoginType(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter(this.loginTypeParameter);
    }

    private String obtainPassword(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter(this.passwordParameter);
    }

    private String obtainUsername(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter(this.usernameParameter);
    }


    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setGrantTypeParameter(String loginTypeParameter) {
        Assert.hasText(loginTypeParameter, "login_type parameter must not be empty or null");
        this.loginTypeParameter = loginTypeParameter;
    }
}
