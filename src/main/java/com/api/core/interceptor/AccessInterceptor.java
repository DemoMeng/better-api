package com.api.core.interceptor;

import com.api.core.annotation.Access;
import com.api.core.redis.RedisService;
import com.api.core.util.WebUtil;
import org.springframework.stereotype.Component;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //放行非 Access 注解的方法
            if(!method.isAnnotationPresent(Access.class)){
                return true;
            }
            Access access = method.getAnnotation(Access.class);
            if(access == null){
                return true;
            }
            //键为 ip+接口名称  值为被可以被调用次数
            int times = access.times();
            int seconds = access.seconds();
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
