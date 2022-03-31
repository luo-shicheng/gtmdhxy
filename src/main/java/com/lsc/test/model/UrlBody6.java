package com.lsc.test.model;

import javax.validation.constraints.NotBlank;

public class UrlBody6 {

    @NotBlank(message = "develop的id不能为空")
    private String id;

    @NotBlank(message = "id1不能为空")
    private String id1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }
}
