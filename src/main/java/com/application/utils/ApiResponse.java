package com.application.utils;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private String messageCode;
    private T data;
    public String toJson(){
        String jsonString = new Gson().toJson(this);
        return jsonString;
    }
}
