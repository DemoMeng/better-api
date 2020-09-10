package com.api.core.annotation;

import java.lang.annotation.*;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access {

    int times() default 3;
    int seconds() default 60;


}
