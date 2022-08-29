package com.example.demo.entity;

import lombok.Data;

@Data
public class Result {
    //相应码
    private Integer code;
    //信息
    private String msg;
    //返回数据
    private Object data;
    //token
    private String token;

    private String image_name;



    public Result() {
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg, Object data, String token) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, String image_name) {
        this.code = code;
        this.msg = msg;
        this.image_name = image_name;
    }
}