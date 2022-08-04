package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@ApiModel(value = "Register对象")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterParamBean {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "邮箱",required = false)
    private String e_mail;
    @ApiModelProperty(value = "手机号码",required = true)
    private String phoneNumber;
    @ApiModelProperty(value = "地址",required = false)
    private String add;
}
