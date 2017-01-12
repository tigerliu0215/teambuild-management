package com.oocl.com.teambuildmanagement.model.vo;

import com.oocl.com.teambuildmanagement.model.vo.Comment;
import com.oocl.com.teambuildmanagement.model.vo.Operator;

import java.util.List;

/**
 * Created by YUJO2 on 1/10/2017.
 */

public class TeamActivity {
    private String _id;
    private Operator updatedBy;
    private String updated;
    private Operator createdBy;
    private String created;
    private List<String> collects;
    private List<String> likes;
    private List<Comment> comments;
    private List<Vote> votings;
    private List<Attachment> attachments;
    private String htmlContent;
    private String title;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Operator getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Operator updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Operator getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Operator createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<String> getCollects() {
        return collects;
    }

    public void setCollects(List<String> collects) {
        this.collects = collects;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Vote> getVotings() {
        return votings;
    }

    public void setVotings(List<Vote> votings) {
        this.votings = votings;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
