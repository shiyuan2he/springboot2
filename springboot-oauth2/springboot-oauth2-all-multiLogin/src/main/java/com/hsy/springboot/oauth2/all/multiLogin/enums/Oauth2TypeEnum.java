package com.hsy.springboot.oauth2.all.multiLogin.enums;

import lombok.Getter;

/**
 * @author heshiyuan
 * @description <p></p>
 * @path springboot2/com.hsy.springboot.oauth2.all.enums
 * @date 24/02/2019 18:45
 * @github http://github.com/shiyuan2he
 * @email shiyuan4work@126.com
 * Copyright (c) 2019 shiyuan4work@126.com All rights reserved.
 * @price ¥5    微信：hewei1109
 */
public enum Oauth2TypeEnum {
    /**
     * 客户端授权
     */
    CLIENT_CREDENTIALS(0, "client_credentials", "客户端授权"),
    PASSWORD(1, "password", "密码授权"),
    AUTHORIZATION_CODE(2, "authorization_code", "授权码授权"),
    IMPLICIT(3, "implicit", "简化模式授权"),

    /**
     *
     */
    REFRESH_TOKEN(-1, "refresh_token", "刷新token模式")
    ;
    @Getter
    private Integer type;
    /**
     * 授权类型
     */
    @Getter
    private String code;
    /**
     * 授权描述
     */
    @Getter
    private String description;

    Oauth2TypeEnum(Integer type, String code, String description) {
        this.type = type;
        this.code = code;
        this.description = description;
    }
}
