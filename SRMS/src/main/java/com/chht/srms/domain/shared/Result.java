package com.chht.srms.domain.shared;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMsg("操作成功");
        return result;
    }

    public static Result<Void> fail(String message) {
        Result<Void> result = new Result<>();
        result.setCode(1);
        result.setMsg(message);
        return result;
    }
}