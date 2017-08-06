package com.sillyhat.base.dto;

import com.sillyhat.base.utils.Constants;

/**
 * SillyHatAJAX
 *
 * @author 徐士宽
 * @date 2017/8/6 20:21
 */
public class SillyHatAJAX {
    private int code;

    private Object data;

    private String msg;

    public SillyHatAJAX(){

    }

    public SillyHatAJAX(int code){
        setCode(code);
    }

    public SillyHatAJAX(int code, String msg){
        setCode(code);
        setMsg(msg);
    }

    public SillyHatAJAX(int code, Object data, String msg){
        setCode(code);
        setData(data);
        setMsg(msg);
    }

    public SillyHatAJAX(int code, Object data){
        setCode(code);
        setData(data);
    }

    public SillyHatAJAX(Object data){
        setCode(Constants.SILLYHAT_AJAX_SUCCESS);
        setMsg(Constants.SILLYHAT_AJAX_SUCCESS_MSG);
        setData(data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
