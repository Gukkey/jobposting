package com.gukkey.jobposting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gukkey.jobposting.model.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
    
}
