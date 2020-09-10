package com.api.core.annotation;

import com.api.core.exception.XException;
import com.api.core.redis.RedisService;
import com.api.core.util.WebUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */

@Aspect
@Component
public class LimitAspect {


//    @Autowired
//    private HttpServletRequest request

    @Pointcut("@annotation(Limit)")
    public void deal(){
    }

    @Around("@annotation(limit)")
    public Object around(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        int times = limit.times();
        String methodName = limit.methodName();
        String key = limit.key();
        long timeOut = limit.timeOut();

        /**
         * 方式1. 从获取RequestAttributes中获取HttpServletRequest的信息
         * 方式2.  @Autowired HttpServletRequest 注入request对象
         * */
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        /**
         * 此处应该加入获取登录用户，当然也可以不用登录
         *      a.登录用户：根据登录用户唯一标识，并且设置每个接口调用次数
         *      b.非登录用户：根据接口名称，接口参数值，client的ip设置成key，并且设置成过期时间。
         * */
        String uKey = new StringBuffer()
                .append(WebUtil.getClientIP(request))
                .append("-")
                .append(methodName)
                .append("-")
                .append(key).toString();
        Object uValue = RedisService.get(uKey);
        if(uValue == null){
            RedisService.set(uKey, times, timeOut);
        }else{
            //存在
            int i = (int)uValue;
            if(i > 0){
                RedisService.set(uKey, --i,timeOut);
            } else {
                throw new XException("系统繁忙，请重试!");
            }
        }
        return joinPoint.proceed();
    }








}
