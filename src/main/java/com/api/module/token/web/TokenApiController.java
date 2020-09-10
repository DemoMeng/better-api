package com.api.module.token.web;

import com.api.core.annotation.Access;
import com.api.core.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@RestController
@RequestMapping("/token")
public class TokenApiController {


    @GetMapping("/1")
    @Access()
    public Result charge(){
        return Result.SUCCESS("请求需要携带token");
    }


}
