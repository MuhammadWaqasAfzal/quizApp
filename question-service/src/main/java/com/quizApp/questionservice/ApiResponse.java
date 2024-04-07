package com.quizApp.questionservice;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private int status;
    private String message;
    private Map<String, T> data = new HashMap<>();

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        //this.data = (data == null) ? (T) Collections.emptyMap() : data;
        this.data.put("result", data == null ? (T) Collections.emptyList() : data);

       // this.data = data;
    }

    // Getters and setters

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }
}
