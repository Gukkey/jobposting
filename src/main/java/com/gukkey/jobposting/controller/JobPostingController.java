package com.gukkey.jobposting.controller;

import com.gukkey.jobposting.model.DTO.JobPostingDTO;
import com.gukkey.jobposting.model.response.JobPostingResponse;
import com.gukkey.jobposting.service.JobPostingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@Tag(name = "Job Postings", description = "API for managing job postings")
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    @PostMapping
    @Operation(summary = "Create a new job posting")
    public ResponseEntity<JobPostingResponse> createJobPosting(@RequestBody JobPostingDTO jobPostingDTO) {
        JobPostingResponse response = jobPostingService.createJobPosting(jobPostingDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit an existing job posting")
    public ResponseEntity<JobPostingResponse> editJobPosting(@PathVariable long id, @RequestBody JobPostingDTO jobPostingDTO) {
        JobPostingResponse response = jobPostingService.editJobPosting(id, jobPostingDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a job posting by ID")
    public ResponseEntity<JobPostingResponse> getJobPosting(@PathVariable long id) {
        JobPostingResponse response = jobPostingService.getJobPosting(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a job posting")
    public ResponseEntity<JobPostingResponse> deleteJobPosting(@PathVariable long id) {
        JobPostingResponse response = jobPostingService.deleteJobPostingResponse(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}