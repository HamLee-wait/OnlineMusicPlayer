package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.RegisterParamBean;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
public interface IUserService extends IService<User> {

    RespBean register(RegisterParamBean registerParam);

    RespBean login(String username, String password, String code, HttpServletRequest request);

}
