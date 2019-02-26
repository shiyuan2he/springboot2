package com.hsy.springboot.oauth2.all.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author heshiyuan
 * @description <p></p>
 * @path springboot2/com.hsy.springboot.oauth2.all.controller
 * @date 26/02/2019 22:26
 * @github http://github.com/shiyuan2he
 * @email shiyuan4work@126.com
 * Copyright (c) 2019 shiyuan4work@126.com All rights reserved.
 * @price ¥5    微信：hewei1109
 */
@Controller
public class TestController {

    @GetMapping(value = "/{path}/hello")
    @ResponseBody
    public String testHello(@PathVariable(value = "path") String path){
        return "hello ," + path;
    }
}
