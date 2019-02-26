package com.hsy.springboot2.security.service;

public interface IUserService {
    /**
     * 判断登陆类型
     * @param clientId 客户端id
     * @return
     */
    String guessLoginType(String clientId);
}
