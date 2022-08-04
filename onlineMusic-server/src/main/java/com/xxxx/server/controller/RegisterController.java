package com.xxxx.server.controller;

import com.xxxx.server.pojo.RegisterParamBean;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService userService ;
    @PostMapping("/")
    public RespBean register(@RequestBody RegisterParamBean registerParam){
        return userService.register(registerParam);
    }
}
