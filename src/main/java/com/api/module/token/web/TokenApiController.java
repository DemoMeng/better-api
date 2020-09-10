package com.api.module.token.web;

import com.api.core.annotation.Tourist;
import com.api.core.redis.RedisService;
import com.api.core.result.Result;
import com.api.core.token.TokenBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@RestController
@RequestMapping("/token")
public class TokenApiController {

    /**存放token的key*/
    private final static String CURRENT_TOKEN_UID = "A_CURRENT_TOKEN_UID_";
    /**存放uId的key*/
    private final static String CURRENT_UID = "A_CURRENT_UID_";


    @GetMapping("/1")
    public Result charge(){
        return Result.SUCCESS("请求需要携带token");
    }

    @GetMapping("/get-token")
    @Tourist()
    public Result getToken(@PathVariable String uId){
        String key = CURRENT_TOKEN_UID+uId;
        String value = (String) RedisService.get(key);
        if(!StringUtils.isEmpty(value)){
            RedisService.del(key);
        }
        String uuid = UUID.randomUUID().toString();
        String jwt = TokenBuilder.build(uuid,uId,1);
        RedisService.set(uuid,jwt,3600);
        return Result.SUCCESS(uuid);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());

    }




}
