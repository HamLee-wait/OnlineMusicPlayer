package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.config.JwtUtils;
import com.xxxx.server.mapper.UserMapper;
import com.xxxx.server.pojo.RegisterParamBean;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.User;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.service.IUserService;
import com.xxxx.server.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Value("${jwt.tokenHeader}")
    private String tokenHead;

    @Override
    public RespBean register(RegisterParamBean registerParam) {
        Map<String,Object> map= MD5Utils.encryption(registerParam.getPassword());
        //先判断用户名是否存在，不允许用户名相同
        int count=userMapper.getNumberByUsername(registerParam.getUsername());
        System.out.println(count);
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
        UserDetails userDetails= userDetailsService.loadUserByUsername(username);
        String salt =userMapper.getSaltByUsername(username);
        String encryption=MD5Utils.encryption(password,salt);
//        System.out.println("****************Log1 登录Impl************************");
//        System.out.println("encryption"+encryption);
//        System.out.println(userDetails.getPassword());
//        System.out.println(passwordEncoder.matches(encryption,userDetails.getPassword()));
        if(userDetails==null|| !userDetails.getPassword().equals(encryption))
            return RespBean.error("用户名或密码不正确！");
        String captcha=(String)request.getSession().getAttribute("captcha");
        HttpSession session=request.getSession();
        System.out.println(captcha);
        if(StringUtils.isEmpty(code)||!captcha.equals(code)){
            return RespBean.error("验证码错误，请重新输入验证码!");
        }
        //登录成功，生成一个token，更新security用户对象
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token=jwtUtils.generatorToken(userDetails);
        Map<String,String> map=new HashMap<>();
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        //把username放到session里面方便之后使用
        request.getSession().setAttribute("username",username);
        return RespBean.success("登录成功！",map);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }


}
