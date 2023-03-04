package com.aquarium.exception;

import cn.hutool.http.HttpException;
import com.aquarium.response.ResponseCode;
import com.aquarium.response.ResponseVo;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author: Ky2Fe
 * @program: ky-vue-background
 * @description: 控制层异常处理
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 数据库异常
     *
     * @param e
     * @return ResponseVo
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ResponseVo sqlException(SQLException e) {
        log.error("数据库异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.SQL_ERROR);
    }

    /**
     * 空指针异常
     *
     * @param e
     * @return ResponseVo
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseVo nullPointExceptionHandler(NullPointerException e) {
        log.warn("空指针异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.NULL_POINT_ERROR);
    }


    /**
     * 处理传入体参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseVo methodArgumentNotValidExceptionHandler(BindException e) {
        //获取错误信息
        ObjectError bindError = e.getBindingResult().getAllErrors().get(0);
        log.error("传入体参数校验异常---{}", bindError.getDefaultMessage());
        return ResponseVo.exp().status(ResponseCode.VALID_DATA);
    }

    /**
     * 处理传入参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseVo methodArgumentNotValidExceptionHandler(MissingServletRequestParameterException e) {
        //获取错误信息
        log.error("传入参数校验异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.VALID_DATA);
    }

    /**
     * 处理HTTP异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServletException.class)
    @ResponseBody
    public ResponseVo servletExceptionHandler(ServletException e) {
        //获取错误信息
        log.error("HTTP异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.HTTP_ERROR);
    }

    /**
     * 处理传入参数空缺异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseBody
    public ResponseVo illegalStateExceptionHandler(IllegalStateException e) {
        //获取错误信息
        log.error("传入参数空缺---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.PARAMS_ERROR);
    }

    /**
     * 处理传入参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseVo methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        //获取错误信息
        log.error("传入参数类型异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.PARAMS_TYPE_ERROR);
    }

    /**
     * 处理COS客户端异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = CosClientException.class)
    @ResponseBody
    public ResponseVo cosClientExceptionHandler(CosClientException e) {
        //获取错误信息
        log.error("COS客户端异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.COS_CLIENT_ERROR);
    }

    /**
     * 处理COS服务端异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = CosServiceException.class)
    @ResponseBody
    public ResponseVo cosServiceExceptionHandler(CosServiceException e) {
        //获取错误信息
        log.error("COS服务端异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.COS_SERVICE_ERROR);
    }

    /**
     * 处理IO异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public ResponseVo iOExceptionHandler(IOException e) {
        //获取错误信息
        log.error("IO异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.IO_ERROR);
    }

    /**
     * 处理人脸机请求异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpException.class)
    @ResponseBody
    public ResponseVo httpExceptionHandler(HttpException e) {
        //获取错误信息
        log.error("人脸机请求异常---{}", e.getMessage());
        return ResponseVo.exp().status(ResponseCode.DEVICE_REQUEST_ERROR);
    }


}
