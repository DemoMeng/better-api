package com.api.module.safe.web;

import com.api.core.annotation.Limit;
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
@RequestMapping("/safe")
public class SafeApiController {


    @GetMapping("/1")
    @Limit(times = 5,timeOut = 60,methodName = "deal",key = "safe-1")
    public Result deal(){
        return Result.SUCCESS("开始执行，现在正常请求不限流");
    }


    @GetMapping("/2")
    public Result handle(){
        return Result.SUCCESS("永不限流的接口~");
    }





}
