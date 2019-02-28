package com.hsy.springboot.oauth2.all.multiLogin.controller;
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

    @GetMapping(value = "/tel")
    public ModelAndView telLogin(){
        return new ModelAndView("login/login-tel");
    }

    @GetMapping(value = "/work-id")
    public ModelAndView workIdLogin(){
        return new ModelAndView("login/login-work-id");
    }
}
