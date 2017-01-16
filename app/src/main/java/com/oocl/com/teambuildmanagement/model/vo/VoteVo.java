package com.oocl.com.teambuildmanagement.model.vo;

import java.util.List;

/**
 * Created by Yummy on 2017/1/16.
 */

public class VoteVo {
    private List<OptionVo> options;
    private String selectionType;
    private String title;
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OptionVo> getOptions() {
        return options;
    }

    public void setOptions(List<OptionVo> options) {
        this.options = options;
    }

    public String getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(String selectionType) {
        this.selectionType = selectionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
