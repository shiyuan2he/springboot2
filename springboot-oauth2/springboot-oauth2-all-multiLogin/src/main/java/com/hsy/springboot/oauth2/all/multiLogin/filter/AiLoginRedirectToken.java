package com.hsy.springboot.oauth2.all.multiLogin.filter;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AiLoginRedirectToken extends AbstractAuthenticationToken {
    @Getter
    private final Object principal;
    @Getter
    private Object credentials;
    @Getter
    private String loginType;

    public AiLoginRedirectToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials, String loginType) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        this.setAuthenticated(true);
    }
    public AiLoginRedirectToken(Object principal, Object credentials, String loginType) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        this.setAuthenticated(false);
    }
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
