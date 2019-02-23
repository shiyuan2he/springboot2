package com.hsy.springboot2.security.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/{userName}/hello")
    public String hello(@PathVariable(value = "userName") String userName){
        return userName + ", hello world";
    }

}
