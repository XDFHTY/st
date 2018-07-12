package com.cj.stcommon.jwt;

import com.cj.stcommon.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtil {

    //加密、解析秘钥
    private static final String key = "secretkey";

    //加密、解析算法
    private static final SignatureAlgorithm mode = SignatureAlgorithm.HS256;
    /**
     * 生成用户token
     * @param userId
     * @param userName
     * @param userType
     * @return
     */
    public static String getUserToken(long time,long userId,String userName,String userType){
        //设置token，有效期
        String userToken = Jwts.builder()
                .claim("userId",userId)
                .claim("userName",userName)
                .claim("userType",userType)
                .setIssuedAt(new Date(time))
                .signWith(mode,key)
                .compact();

        return userToken;
    }

    /**
     * 生成管理员token
     * @param time
     * @param adminId
     * @param adminName
     * @param adminType
     * @return
     */
    public static String getAdminToken(long time,long adminId,String adminName,String adminType){
        //设置token，有效期
        String AdminToken = Jwts.builder()
                .claim("adminId",adminId)
                .claim("adminName",adminName)
                .claim("adminType",adminType)
                .setIssuedAt(new Date(time))
//                    .setExpiration(new Date(time+1000*60*60*2))
                .signWith(mode,key)
                .compact();

        return AdminToken;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims getUserClaims(String token){

        Claims claims = Jwts.parser()
                            .setSigningKey(key)
                            .parseClaimsJws(token)
                            .getBody();
        System.out.println("=============claims===========================");
        System.out.println(claims);
        System.out.println("=============claims===========================");

        return claims;
    }
}
