package com.oocl.com.teambuildmanagement.model.vo;

import java.util.List;

/**
 * Created by YUJO2 on 1/15/2017.
 */

public class TeamActivityVo {
    private List<TeamActivity> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TeamActivity> getData() {

        return data;
    }

    public void setData(List<TeamActivity> data) {
        this.data = data;
    }
}
