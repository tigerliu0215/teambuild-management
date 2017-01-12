package com.oocl.com.teambuildmanagement.model.vo;

import java.util.List;

/**
 * Created by YUJO2 on 1/12/2017.
 */

public class Vote {
    private String visibility;
    private List<Option> options;
    private String selectionType;
    private String title;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
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

    class Option{
        private List<VoteDetail> voteDetails;
        private String description;
        private int sequence;

        public List<VoteDetail> getVoteDetails() {
            return voteDetails;
        }

        public void setVoteDetails(List<VoteDetail> voteDetails) {
            this.voteDetails = voteDetails;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }
    }
    class VoteDetail{
        private String created;
        private String createdBy;

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
}
