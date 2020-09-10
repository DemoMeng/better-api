package com.api.core.exception;

import lombok.Data;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@Data
public class XException extends RuntimeException {

    String msg;

    public XException(){}

    public XException(String msg){
        this.msg = msg;
    }




}
