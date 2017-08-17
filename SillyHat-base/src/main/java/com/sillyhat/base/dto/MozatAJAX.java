package com.sillyhat.base.dto;

import com.sillyhat.base.utils.Constants;

/**
 * SillyHatAJAX
 *
 * @author 徐士宽
 * @date 2017/8/6 20:21
 */

public class MozatAJAX {

    private int ret;

    private Object data;

    private String msg;

    public MozatAJAX(){

    }

    public MozatAJAX(int code){
        setRet(code);
    }

    public MozatAJAX(int code, String msg){
        setRet(code);
        setMsg(msg);
    }

    public MozatAJAX(int code, Object data, String msg){
        setRet(code);
        setData(data);
        setMsg(msg);
    }

    public MozatAJAX(int code, Object data){
        setRet(code);
        setData(data);
    }

    public MozatAJAX(Object data){
        setRet(Constants.SILLYHAT_AJAX_SUCCESS);
        setMsg(Constants.SILLYHAT_AJAX_SUCCESS_MSG);
        setData(data);
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
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
