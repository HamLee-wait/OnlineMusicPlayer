package com.xxxx.server.service.impl;

import com.xxxx.server.mapper.UserMapper;
import com.xxxx.server.pojo.RegisterParamBean;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.service.IUserService;
import com.xxxx.server.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public RespBean register(RegisterParamBean registerParam) {
        Map<String,Object> map= MD5Utils.encryption(registerParam.getPassword());
        //先判断用户名是否存在，不允许用户名相同
        int count=userMapper.getNumberByUsername();
        if(count!=0)
        {
            return RespBean.error("该用户已存在，请重新输入用户名");
        }
        //用户名检验合格之后将数据存入map中方便进一步入库
        try {
            map.put("username",registerParam.getUsername());
            map.put("add",registerParam.getAdd());
            map.put("e-mail",registerParam.getE_mail());
            map.put("phoneNumber",registerParam.getPhoneNumber());
            userMapper.register(map);
            return RespBean.success("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("系统内部错误");

        }
    }

    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        String captcha=(String)request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(captcha)||!captcha.equalsIgnoreCase(code))//不区分大小写
        {
            return RespBean.error("验证码输入错误,请重新输入!");
        }
        return null;
    }
}
