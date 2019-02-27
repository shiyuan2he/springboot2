package com.hsy.springboot.oauth2.service.impl;//package com.hsy.springboot.oauth2.server;

import com.hsy.springboot.oauth2.all.bean.entity.TOauthClient;
import com.hsy.springboot.oauth2.all.enums.Oauth2TypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service(value = "clientDetailServiceImpl")
public class ClientDetailServiceImpl implements ClientDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info(clientId);
        if(StringUtils.isBlank(clientId)){
            log.warn("clientId is null");
            return null;
        }

        BaseClientDetails client = null;
        client = new BaseClientDetails();
        client.setClientId(clientId);
        client.setClientSecret(passwordEncoder.encode("secret"));
        client.setResourceIds(Arrays.asList("resourceId"));
        client.setAuthorizedGrantTypes(Arrays.asList(""
                .split(",")));
        //不同的client可以通过 一个scope 对应 权限集
        client.setScope(Arrays.asList(oauthInfo.getScope()));

        client.setAutoApproveScopes(Arrays.asList(oauthInfo.getScope()));
        //分配不同的角色权限
        client.setAuthorities(AuthorityUtils.createAuthorityList(oauthInfo.getAuthorities()));
        //3分钟
        client.setAccessTokenValiditySeconds(oauthInfo.getAccessTokenValiditySeconds());
        //3分钟
        client.setRefreshTokenValiditySeconds(oauthInfo.getRefreshTokenValiditySeconds());
        //登录跳转链接
        Set<String> uris = new HashSet<>();
        uris.add(oauthInfo.getRedirectUri());
        client.setRegisteredRedirectUri(uris);
        if(client == null) {
            throw new NoSuchClientException("No client width requested id: " + clientId);
        }
        return client;
    }
}
