package com.xxxx.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/output")
    public String Hello(HttpServletRequest request){
        return request.toString();
    }
}
