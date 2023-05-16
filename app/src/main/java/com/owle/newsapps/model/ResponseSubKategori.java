package com.owle.newsapps.model;

import java.util.List;

public class ResponseSubKategori {

    String code, message;

    List<SubKategori> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubKategori> getData() {
        return data;
    }

    public void setData(List<SubKategori> data) {
        this.data = data;
    }
}
