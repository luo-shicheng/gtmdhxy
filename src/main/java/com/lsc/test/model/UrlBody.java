package com.lsc.test.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class UrlBody {

    @NotBlank(message = "name不能为空",groups = {Write.class})
    private String name;

    @NotBlank(message = "type不能为空",groups = {Read.class})
    private String type;

    private String url;

    @Valid
    private UrlBody2 urlBody2s;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UrlBody2 getUrlBody2s() {
        return urlBody2s;
    }

    public void setUrlBody2s(UrlBody2 urlBody2s) {
        this.urlBody2s = urlBody2s;
    }
}
