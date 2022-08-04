package com.xxxx.server.utils;

//import org.springframework.util.DigestUtils;
import org.apache.commons.codec.digest.DigestUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MD5Utils {
    public static Map<String,Object> encryption(String password){
            Map<String,Object> map=new HashMap<>();
            String salt=getSaltString();
            String encryption=DigestUtils.md5Hex(salt+password);
            map.put("encryption",encryption);
            map.put("salt",salt);
            return map;

    }
    //生成6位随机字符串的盐，由数字、大小写字母组成
    public static String getSaltString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<6;i++){
            int num=random.nextInt(str.length());
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }

}
