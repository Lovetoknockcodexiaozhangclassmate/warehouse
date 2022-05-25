package com.zhang.warehouse.code;

import lombok.Data;

/**
 * @author 1zhang
 */
@Data
public class ResponseData implements StatusCode{
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 成功
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData success(String message,Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(OK);
        return responseData;
    }
    /**
     * 失败
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData error(String message,Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(ERROR);
        return responseData;
    }
    /**
     * 账号或密码错误
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData loginError(String message,Object data){
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(LOGINERROR);
        return responseData;
    }
    /**
     * 权限不足
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData accessError(String message,Object data){
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(ACCESSERROR);
        return responseData;
    }
    /**
     * 远程调用失败
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData remotError(String message,Object data){
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(REMOTEERROR);
        return responseData;
    }
    /**
     * 重复操作
     * @param message ：返回的信息
     * @param data ：返回的数据
     * @return :
     */
    public static ResponseData repError(String message,Object data){
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setCode(REPERROR);
        return responseData;
    }
}
