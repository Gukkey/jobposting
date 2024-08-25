package com.gukkey.jobposting.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gukkey.jobposting.model.JobPosting;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobPostingResponse {
    protected int status;
    protected String message;
    protected JobPosting jobPosting;
}
