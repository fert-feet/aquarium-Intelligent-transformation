package com.aquarium.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseVo {

    /**
     * 状态代码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 成功返回结果
     *
     * @return resultVo
     */
    public static ResponseVo success() {
        ResponseVo resultVo = new ResponseVo();
        resultVo.setCode(ResponseCode.SUCCESS.getCode());
        resultVo.setMsg(ResponseCode.SUCCESS.getMsg());
        return resultVo;
    }

    /**
     * 失败返回结果
     *
     * @return resultVo
     */
    public static ResponseVo exp() {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseCode.FAIL.getCode());
        responseVo.setMsg(ResponseCode.FAIL.getMsg());
        return responseVo;
    }

    /**
     * 自定义状态，使用枚举类里的状态和状态码
     *
     * @param status
     * @return
     */
    public ResponseVo status(ResCode status) {
        this.setMsg(status.getMsg());
        this.setCode(status.getCode());
        return this;
    }

    /**
     * 自定义数据，支持链式调用
     *
     * @param key
     * @param data
     * @return this
     */
    public ResponseVo data(String key, Object data) {
        this.data.put(key, data);
        return this;
    }
}
