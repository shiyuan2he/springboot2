package com.hsy.springboot2.security.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/view")
public class ViewController {

    @GetMapping(value = "/username")
    public ModelAndView usernameLogin(){
        return new ModelAndView("login/login-username");
    }
}
