package com.oocl.com.teambuildmanagement.model.vo;

/**
 * Created by YUJO2 on 1/12/2017.
 */

public class Comment {
    private int sequence;
    private String content;
    private String created;
    private String createdBy;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
