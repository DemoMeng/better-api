package com.api.core.annotation;

import java.lang.annotation.*;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /** 调用次数最高 10次 */
    int times() default 10;
    /** 限时调用间隔时间 120s */
    long timeOut() default 120;
    /** 接口方法名（这可以用request获取） */
    String methodName() default "";
    /** 接口key */
    String key() default "";
}
