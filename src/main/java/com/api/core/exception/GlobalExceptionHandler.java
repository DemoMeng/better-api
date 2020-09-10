package com.api.core.exception;

import com.api.core.result.Result;
import com.api.core.result.ResultEnum;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author mqz
 * @description
 * @since 2020/9/10
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return Result.ERROR(ResultEnum.BAD_REQUEST);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return Result.ERROR(ResultEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Result handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();
        return Result.ERROR(ResultEnum.UNSUPPORTED_MEDIA_TYPE);
    }



    /** ==========================自定义异常======================== */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(
            Exception e) {
        e.printStackTrace();
        return Result.ERROR(ResultEnum.ERROR);
    }

    @ExceptionHandler(XException.class)
    @ResponseBody
    public Result handleXException(
            XException e) {
        e.printStackTrace();
        return Result.ERROR(e.getMsg());
    }

    /**
     * 404 - Not Found
     */
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(Not)
//    @ResponseBody
//    public Result handleNotFoundException(
//            ClassNotFoundException e) {
//        e.printStackTrace();
//        return Result.ERROR(ResultEnum.NOT_FOUND);
//    }









}
