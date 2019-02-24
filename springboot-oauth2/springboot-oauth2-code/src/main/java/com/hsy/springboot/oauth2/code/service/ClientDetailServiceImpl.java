package com.hsy.springboot.oauth2.code.service;//package com.hsy.springboot.oauth2.server;
import com.hsy.springboot.oauth2.code.bean.entity.TOauthClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.*;

@Slf4j
//@Service(value = "clientDetailsService")
public class ClientDetailServiceImpl implements ClientDetailsService {

//    @Autowired
//    TOauthClientMapper tOauthClientMapper;

    private List<TOauthClient> createOauthClient() {
        List<TOauthClient> list = new ArrayList<>();
        TOauthClient tOauthClient = new TOauthClient();
        tOauthClient.setAccessTokenValiditySeconds(30);
        tOauthClient.setAuthorities("user");
        tOauthClient.setAuthorizedGrantTypes("client_credentials,refresh_token");
        tOauthClient.setClientId("client");
        tOauthClient.setClientSecret("secret");
        tOauthClient.setRedirectUri("www.baidu.com");
        tOauthClient.setRefreshTokenValiditySeconds(30);
        tOauthClient.setResourceIds("resourceId");
        tOauthClient.setScope("ALL");
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
        List<TOauthClient> list = createOauthClient();
        if(null!=list && list.size()>0) {
            TOauthClient oauthInfo = list.get(0);
            client = new BaseClientDetails();
            client.setClientId(clientId);
            client.setClientSecret("{noop}"+oauthInfo.getClientSecret());
            client.setResourceIds(Arrays.asList(oauthInfo.getResourceIds()));
            client.setAuthorizedGrantTypes(Arrays.asList(oauthInfo.getAuthorizedGrantTypes()));
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
