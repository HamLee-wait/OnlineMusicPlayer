package com.xxxx.server.controller;

import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "LoginController")
public class LoginController {
    @Autowired
    private IUserService userService;
    @ApiOperation("登录之后返回token")
    @PostMapping("/login")
    public RespBean Login(@RequestBody AdminLoginParam adminLoginParam,HttpServletRequest request){
        return userService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }
    @ApiOperation(value = "注销功能")
    @PostMapping("/logout")
    public RespBean Logout(){
        return RespBean.success("注销成功");
    }
}
