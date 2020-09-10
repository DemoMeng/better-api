package com.api.core.result;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
public enum ResultEnum {


    SUCCESS(200,"成功"),
    ERROR(500,"失败"),
    NOT_FOUND(404,"请求的资源不存在"),
    BAD_REQUEST(400, "错误的请求"),
    METHOD_NOT_ALLOWED(405, "当前请求方法不允许"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }
}
