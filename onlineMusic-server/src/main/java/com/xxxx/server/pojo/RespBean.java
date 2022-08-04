package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private int code;//状态码
    private String message;//携带的信息
    private Object object;//返回给前端的对象（可能不一定存在）
    //前端访问接口成功
    public static RespBean success(String message){
        return new RespBean(200,message,null);//成功返回，状态码200
    }
    public static RespBean success(String message,Object object){
        return new RespBean(200,message,object);
    }

    //错误返回
    public static RespBean error(String message){return new RespBean(500,message,null);}
    public static RespBean error(String message,Object o){return new RespBean(500,message,o);}
}
