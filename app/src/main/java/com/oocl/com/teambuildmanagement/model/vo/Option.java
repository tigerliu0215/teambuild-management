package com.oocl.com.teambuildmanagement.model.vo;

import java.util.List;

/**
 * Created by Yummy on 2017/1/16.
 */

public class Option{
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
