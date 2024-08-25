package com.gukkey.jobposting.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gukkey.jobposting.model.JobPosting;
import com.gukkey.jobposting.model.DTO.JobPostingDTO;
import com.gukkey.jobposting.model.response.JobPostingResponse;
import com.gukkey.jobposting.repository.JobPostingRepository;

@Service
public class JobPostingService {

    @Autowired
    JobPostingRepository jobPostingRepository;

    public JobPostingResponse createJobPosting(JobPostingDTO jobPostingDTO) {
        JobPosting jobposting = JobPosting.builder().title(jobPostingDTO.getTitle())
                .description(jobPostingDTO.getDescription()).company(jobPostingDTO.getCompany())
                .salaryRange(jobPostingDTO.getSalaryRange()).requiredSkills(jobPostingDTO.getRequiredSkills())
                .applicationDeadline(LocalDate.parse(jobPostingDTO.getApplicationDeadline())).build();
        jobPostingRepository.save(jobposting);
        JobPostingResponse jobPostingResponse = JobPostingResponse.builder().status(201).message("Job Posting has been created").jobPosting(jobposting).build();
        return jobPostingResponse;
    }

    public JobPostingResponse editJobPosting(long id, JobPostingDTO jobPostingDTO) {
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(id);
        JobPostingResponse jobPostingResponse = null;
        if(!jobPosting.isPresent()) {
            jobPostingResponse = JobPostingResponse.builder().status(404).message("Not found").build();
            return jobPostingResponse;
        }
        jobPosting.get().setTitle(jobPostingDTO.getTitle());
        jobPosting.get().setDescription(jobPostingDTO.getDescription());
        jobPosting.get().setCompany(jobPostingDTO.getCompany());
        jobPosting.get().setSalaryRange(jobPostingDTO.getSalaryRange());
        jobPosting.get().setApplicationDeadline(LocalDate.parse(jobPostingDTO.getApplicationDeadline()));
        jobPosting.get().setRequiredSkills(jobPostingDTO.getRequiredSkills());
        jobPostingRepository.saveAndFlush(jobPosting.get());
        jobPostingResponse = JobPostingResponse.builder().status(200).message("edit has been made").build();
        return jobPostingResponse;
    }

    public JobPostingResponse getJobPosting(long id) {
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(id);
        JobPostingResponse jobPostingResponse = null;
        if(!jobPosting.isPresent()) {
            jobPostingResponse = JobPostingResponse.builder().status(404).message("Not found").build();
            return jobPostingResponse;
        }
        jobPostingResponse = JobPostingResponse.builder().status(200).message("Job posting found").jobPosting(jobPosting.get()).build();
        return jobPostingResponse;
    }

    public JobPostingResponse deleteJobPostingResponse(long id) {
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(id);
        JobPostingResponse jobPostingResponse = null;
        if(!jobPosting.isPresent()) {
            jobPostingResponse = JobPostingResponse.builder().status(404).message("Not found").build();
            return jobPostingResponse;
        }
        jobPostingRepository.deleteById(id);
        jobPostingResponse = JobPostingResponse.builder().status(200).message("Job posting has been deleted").build();
        return jobPostingResponse;
    }
}
