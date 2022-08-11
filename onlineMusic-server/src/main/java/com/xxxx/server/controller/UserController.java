package com.xxxx.server.controller;


import com.xxxx.server.config.JwtUtils;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;
import com.xxxx.server.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @ApiOperation("获取当前用户信息")
    @GetMapping("/user/info")
    public RespBean getUser(Principal principal){
       if (principal==null)
           return null;
        String username=principal.getName();
        User user =userService.getUserByUsername(username);
        user.setPassword(null);
        user.setSalt(null);
        return RespBean.success("获取当前用户信息成功",user);
    }

}
