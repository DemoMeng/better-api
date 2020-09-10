package com.api.core.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
public class TokenBuilder {

    private final static String secret = "MQZSOHANDSOME";


    private static Date getExpire(Integer hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,hours==null?2:hours);
        return calendar.getTime();
    }


    public static String build(String uuid,String uId,Integer hours) {
        //签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //密钥签名
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm, signingKey)
                .claim("uId", uId)
                .claim("uuid", uuid);
                //.setExpiration(getExpire(hours));
        return builder.compact();
    }

    public static Claims parse(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }


    public static void main(String[] args) {

        String uuid = UUID.randomUUID().toString();
        String jwt = build(uuid,"1",2);
        System.out.println("uuid:"+uuid+",jwt:"+jwt);



        System.out.println(parse(jwt));





    }





}
