package com.owle.newsapps.model;

import java.util.List;

public class ResponseBerita {

    String code, message;

    List<Berita> data;

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

    public List<Berita> getData() {
        return data;
    }

    public void setData(List<Berita> data) {
        this.data = data;
    }
}
