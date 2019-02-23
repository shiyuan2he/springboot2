//package com.hsy.springboot.oauth2.server;
//
//import com.lima.apiserver.configuration.oauth.Oauth2Constant;
//import com.lima.apiserver.currentCenter.dao.OauthInfoMapper;
//import com.lima.apiserver.currentCenter.entity.OauthInfo;
//import com.lima.apiserver.oauthCenter.enums.Oauth2RolesEnum;
//import com.lima.apiserver.oauthCenter.enums.Oauth2ScopeEnum;
//import com.lima.apiserver.oauthCenter.enums.Oauth2TypeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.security.oauth2.provider.NoSuchClientException;
//import org.springframework.security.oauth2.provider.client.BaseClientDetails;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created with IntelliJ IDEA.
// * User: changjiang
// * Date: 2019/1/23
// * Time: 16:28
// * To change this template use File | Settings | File Templates.
// */
//@Slf4j
//public class BaseClientDetailService implements ClientDetailsService {
//
//    @Autowired
//    private OauthInfoMapper oauthInfoMapper;
//
//    @Override
//    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        log.info(clientId);
//        if(StringUtils.isBlank(clientId)){
//            log.warn("clientId is null");
//            return null;
//        }
//
//        List<OauthInfo> oauthInfos = oauthInfoMapper.getListByClientId(clientId);
//        BaseClientDetails client = null;
//        if(null!=oauthInfos&&oauthInfos.size()>0) {
//            OauthInfo oauthInfo = oauthInfos.get(0);
//            client = new BaseClientDetails();
//            client.setClientId(clientId);
//            client.setClientSecret("{noop}"+oauthInfo.getClientSecret());
//            client.setResourceIds(Arrays.asList(Oauth2Constant.RESOURCE_ID));
//            client.setAuthorizedGrantTypes(Arrays.asList(
//                    Oauth2TypeEnum.CLIENT_CREDENTIALS.getCode(),
//                    Oauth2TypeEnum.PASSWORD.getCode(),
//                    Oauth2TypeEnum.AUTHORIZATION_CODE.getCode(),
//                    Oauth2TypeEnum.REFRESH_TOKEN.getCode()));
//            //不同的client可以通过 一个scope 对应 权限集
//            client.setScope(Arrays.asList(Oauth2ScopeEnum.ALL.getCode(), Oauth2ScopeEnum.SELECT.getCode()));
//
//            client.setAutoApproveScopes(Arrays.asList(Oauth2ScopeEnum.ALL.getCode()));
//            //分配不同的角色权限
//            client.setAuthorities(AuthorityUtils.createAuthorityList(Oauth2RolesEnum.CBC.getCode()));
//            //3分钟
//            client.setAccessTokenValiditySeconds((int)TimeUnit.SECONDS.toSeconds(180));
//            //3分钟
//            client.setRefreshTokenValiditySeconds((int)TimeUnit.SECONDS.toSeconds(180));
//            //登录跳转链接
//            Set<String> uris = new HashSet<>();
//            uris.add(oauthInfo.getRedirectUri());
//            client.setRegisteredRedirectUri(uris);
//        }
//        if(client == null) {
//            throw new NoSuchClientException("No client width requested id: " + clientId);
//        }
//        return client;
//    }
//}
