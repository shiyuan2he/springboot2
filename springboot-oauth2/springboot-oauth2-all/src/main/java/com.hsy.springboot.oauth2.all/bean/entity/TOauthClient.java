package com.hsy.springboot.oauth2.all.bean.entity;

import lombok.Data;

/**
 * @author heshiyuan
 * @description <p></p>
 * @path springboot2/com.hsy.springboot.oauth2.all.bean.entity
 * @date 24/02/2019 21:36
 * @github http://github.com/shiyuan2he
 * @email shiyuan4work@126.com
 * Copyright (c) 2019 shiyuan4work@126.com All rights reserved.
 * @price ¥5    微信：hewei1109
 */
@Data
public class TOauthClient {
    private Long id;
    private String clientId;
    private String clientSecret;
    private Byte oauthType;
    private Byte type;
    private String redirectUri;
    private Long orgId;
    private Long createTime;
    private Long updateTime;
    private Integer version;
    private Byte isDel;
    private String resourceIds;
    private String scope;
    private String authorizedGrantTypes;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private String authorities;

}
