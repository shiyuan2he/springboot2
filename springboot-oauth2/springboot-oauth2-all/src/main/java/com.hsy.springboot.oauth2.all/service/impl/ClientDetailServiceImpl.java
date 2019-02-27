package com.hsy.springboot.oauth2.all.service.impl;//package com.hsy.springboot.oauth2.server;

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
//    @Autowired
//    TOauthClientMapper tOauthClientMapper;

    private List<TOauthClient> createOauthClient(String clientId) {
        List<TOauthClient> list = new ArrayList<>();
        TOauthClient tOauthClient = new TOauthClient();
        tOauthClient.setAccessTokenValiditySeconds(180);
        tOauthClient.setAuthorities("user");
        String grantType = Oauth2TypeEnum.CLIENT_CREDENTIALS.getCode() + ","
                + Oauth2TypeEnum.PASSWORD.getCode() + ","
                + Oauth2TypeEnum.AUTHORIZATION_CODE.getCode() + ","
                + Oauth2TypeEnum.IMPLICIT.getCode() + ","
                + Oauth2TypeEnum.REFRESH_TOKEN.getCode();
        tOauthClient.setAuthorizedGrantTypes(grantType);
        tOauthClient.setClientId(clientId);
        tOauthClient.setClientSecret(passwordEncoder.encode("secret"));
        tOauthClient.setRedirectUri("http://www.baidu.com");
        tOauthClient.setRefreshTokenValiditySeconds(180);
        tOauthClient.setResourceIds("resourceId");
        tOauthClient.setScope("all");
        list.add(tOauthClient);
        return list;
    }
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info(clientId);
        if(StringUtils.isBlank(clientId)){
            log.warn("clientId is null");
            return null;
        }

        BaseClientDetails client = null;
//        List<TOauthClient> list = tOauthClientMapper.selectClient(clientId);
        List<TOauthClient> list = createOauthClient(clientId);
        if(null!=list && list.size()>0) {
            TOauthClient oauthInfo = list.get(0);
            client = new BaseClientDetails();
            client.setClientId(clientId);
            client.setClientSecret(oauthInfo.getClientSecret());
            client.setResourceIds(Arrays.asList(oauthInfo.getResourceIds()));
            client.setAuthorizedGrantTypes(Arrays.asList(oauthInfo.getAuthorizedGrantTypes().split(",")));
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
        }
        if(client == null) {
            throw new NoSuchClientException("No client width requested id: " + clientId);
        }
        return client;
    }
}
