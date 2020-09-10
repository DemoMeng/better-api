package com.api.core.interceptor;

import com.api.core.annotation.Tourist;
import com.api.core.redis.RedisService;
import com.api.core.util.WebUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private final static int allTime = 0;
    /** 单位：秒 */
    private final static int allSecond = 120;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            /**放行游客*/
            if(method.isAnnotationPresent(Tourist.class)){
                return true;
            }
            String token = request.getHeader("token");
            if(StringUtils.isEmpty(token)){
                output(response,"{\"code\":433,\"msg\":\"token为空\"}");
                return false;
            }
            String jwt = (String) RedisService.get(token);
            if(StringUtils.isEmpty(jwt)){
                output(response,"{\"code\":434,\"msg\":\"token不存在\"}");
                return false;
            }
            //键为 ip+接口名称  值为被可以被调用次数
            int times = allTime;
            int seconds = allSecond;
            String ip = WebUtil.getClientIP(request);
            String key = ip+request.getRequestURI();
            Integer value = (Integer) RedisService.get(key);
            if(value == null){
                RedisService.set(key,times-1,seconds);
            }else{
                int oldTimes = value;
                if(oldTimes <= 0){
                    output(response, "请求太频繁!");
                    return false;

                }
                Long leftTimes = RedisService.getExpire(key);
                RedisService.del(key);
                RedisService.set(key,oldTimes-1,leftTimes);
            }
            return true;
        }
        return true;
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }


}
