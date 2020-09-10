package com.api.core.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@Data
@Accessors(chain = true)
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    private Result(){}

    public static Result SUCCESS(){
        Result result = new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    public static Result SUCCESS(String msg){
        Result result = new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMsg(msg == null?ResultEnum.SUCCESS.getMsg():msg);
        return result;
    }

    public static Result SUCCESS(Object obj){
        Result result = new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMsg(ResultEnum.SUCCESS.getMsg())
                .setData(obj);
        return result;
    }

    public static Result SUCCESS(Object obj,String msg){
        Result result = new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMsg(msg == null?ResultEnum.SUCCESS.getMsg():msg)
                .setData(obj);
        return result;
    }


    public static Result ERROR(){
        Result result = new Result()
                .setCode(ResultEnum.ERROR.getCode())
                .setMsg(ResultEnum.ERROR.getMsg());
        return result;
    }


    public static Result ERROR(String msg){
        Result result = new Result()
                .setCode(ResultEnum.ERROR.getCode())
                .setMsg(msg == null?ResultEnum.ERROR.getMsg():msg);
        return result;
    }

    public static Result ERROR(Object obj){
        Result result = new Result()
                .setCode(ResultEnum.ERROR.getCode())
                .setData(obj);
        return result;
    }

    public static Result ERROR(Object obj,String msg){
        Result result = new Result()
                .setCode(ResultEnum.ERROR.getCode())
                .setMsg(msg == null?ResultEnum.ERROR.getMsg():msg)
                .setData(obj);
        return result;
    }
    public static Result ERROR(ResultEnum resultEnum){
        Result result = new Result()
                .setCode(resultEnum.getCode()==null?ResultEnum.ERROR.getCode():resultEnum.getCode())
                .setMsg(resultEnum.getMsg()==null?ResultEnum.SUCCESS.getMsg():resultEnum.getMsg());
        return result;
    }

}
