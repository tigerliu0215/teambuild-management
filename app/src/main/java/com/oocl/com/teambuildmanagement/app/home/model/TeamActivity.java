package com.oocl.com.teambuildmanagement.app.home.model;

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
    private List<Comment> comments;






    class Comment{
        int sequence;
        String content;
        String created;
        String createdBy;

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

    class Operator{
        String _id;
        String displayName;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}
