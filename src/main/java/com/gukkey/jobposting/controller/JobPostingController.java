package com.gukkey.jobposting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gukkey.jobposting.model.DTO.JobPostingDTO;
import com.gukkey.jobposting.model.response.JobPostingResponse;
import com.gukkey.jobposting.service.JobPostingService;

@RestController
@RequestMapping("/api/")
public class JobPostingController {

    @Autowired
    JobPostingService jobPostingService;
    
    @PostMapping("/job/post")
    public JobPostingResponse postJobPosting(@RequestBody JobPostingDTO jobPostingDTO){
        return jobPostingService.createJobPosting(jobPostingDTO);
    }

    @GetMapping("/job/{id}")
    public JobPostingResponse getJobPostingResponse(@PathVariable long id) {
        return jobPostingService.getJobPosting(id);
    }

    @PutMapping("/job/edit/{id}")
    public JobPostingResponse editJobPosting(@RequestBody JobPostingDTO jobPostingDTO, @PathVariable long id) {
        return jobPostingService.editJobPosting(id, jobPostingDTO);
    }

    @DeleteMapping("/job/delete/{id}")
    public JobPostingResponse deleteJobPosting(@PathVariable long id) {
        return jobPostingService.deleteJobPostingResponse(id);
    }
}
