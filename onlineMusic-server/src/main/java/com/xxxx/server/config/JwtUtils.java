package com.xxxx.server.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtUtils {
    public static final String CLAIM_KEY_USERNAME="sub";
    public static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;//密钥
    @Value("${jwt.expiration}")
    private Long expiration;
    //关于什么是JWT可以看https://www.jianshu.com/p/576dbf44b2ae
    public String generatorToken(UserDetails userDetails)
    {
        //准备荷载
        Map<String,Object> claims= new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generatorToken(claims);
    }

    /**
     * 从token中获取用户名
     * @return
     */
    public String getUsername(String token){
        String username;
        //先通过token获取荷载
        try {
            Claims claims=getClaims(token);
            //然后通过荷载获取用户名
            username=claims.getSubject();
        } catch (Exception e) {
            username=null;
        }
        return username;
    }

    /**
     * 判断token是否失效，包括内容失效和时间过期
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }
    /**
     * 判断token是否过期
     */
    private boolean isTokenExpired(String token) {
        //获取失效时间
        Date expireDate=getExpiredDate(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     * @param token
     * @return
     */
    private Date getExpiredDate(String token) {
        Claims claims=getClaims(token);
        return  claims.getExpiration();
    }

    /**
     * 判断token是否可以被刷新（过期即可刷新嘛！！）
     * @param token
     * @return
     */
    public boolean CanRefresh(String token)
    {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String ReFreshToken(String token)
    {
        Claims claims=getClaims(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generatorToken(claims);
    }
    /**
     * 根据token获取荷载
     * @param token
     * @return
     */
    private Claims getClaims(String token) {
        Claims claims= null;
        try {
            claims= Jwts.parser()
                    .setSigningKey(secret)//放密钥
                    .parseClaimsJws(token)//放荷载
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据荷载生成Token
     * @param claims
     * @return
     */
    private String generatorToken(Map<String,Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token的失效时间
     *  失效时间为当前时间+我们配置的失效时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
