package com.xxxx.server.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 验证码生成接口
 */
@RestController
public class CaptchaController {
    @ApiOperation("生成验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void CreateCaptcha(HttpServletRequest request, HttpServletResponse response)throws Exception{
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        /*****************生成验证码*******************/
        ArithmeticCaptcha captcha=new ArithmeticCaptcha(111,36,3);
        //获取算术验证码的结果
        String result=captcha.text();
        //获取算术表达式
        String text=captcha.getArithmeticString();
        System.out.println(text);
        captcha.out(response.getOutputStream());
    }
}
