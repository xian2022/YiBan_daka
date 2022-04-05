package org.yibanPunchCard.entity;

import com.alibaba.fastjson.JSON;

public class Result {
    private Integer code;
    private String message;
    private String data;

    public static Result resolve(String json){

        return JSON.parseObject(json,Result.class);
    }
    protected static String parse(Result result){
        return JSON.toJSONString(result);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return ((data.equals("{}")||data.equals("[]")) ?"":(data.contains("ADDRESS")?data.substring(data.indexOf("ADDRESS"),data.indexOf(",",data.indexOf("ADDRESS"))):data));
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{"+"code:"
                +code+",message:"
                +message+
                ((data.equals("{}")||data.equals("[]")) ?"":","+(data.contains("ADDRESS")?data.substring(data.indexOf("ADDRESS"),data.indexOf(",",data.indexOf("ADDRESS"))):data))+"}";
    }
}
