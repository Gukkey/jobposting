package com.gukkey.jobposting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gukkey.jobposting.model.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
    JobPosting findByTitle(String title);

    List<JobPosting> findByCompany(String company);
}
