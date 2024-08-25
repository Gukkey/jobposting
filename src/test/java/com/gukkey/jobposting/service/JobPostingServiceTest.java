package com.gukkey.jobposting.service;

import com.gukkey.jobposting.model.JobPosting;
import com.gukkey.jobposting.model.DTO.JobPostingDTO;
import com.gukkey.jobposting.model.response.JobPostingResponse;
import com.gukkey.jobposting.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JobPostingServiceTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingService jobPostingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editJobPosting_ShouldReturnNotFound_WhenJobPostingDoesNotExist() {
        long id = 1L;
        JobPostingDTO jobPostingDTO = new JobPostingDTO();

        when(jobPostingRepository.findById(id)).thenReturn(Optional.empty());

        JobPostingResponse response = jobPostingService.editJobPosting(id, jobPostingDTO);

        assertEquals(404, response.getStatus());
        assertEquals("Not found", response.getMessage());
    }


    @Test
    void editJobPosting_ShouldUpdateAndReturnSuccess_WhenJobPostingExists() {
        long id = 1L;
        JobPosting existingJobPosting = JobPosting.builder()
                .id(id)
                .title("Old Title")
                .description("Old Description")
                .company("Old Company")
                .salaryRange("Old Range")
                .requiredSkills(List.of("Old Skill"))
                .applicationDeadline(LocalDate.of(2024, 9, 1))
                .build();

        JobPostingDTO jobPostingDTO = new JobPostingDTO();
        jobPostingDTO.setTitle("New Title");
        jobPostingDTO.setDescription("New Description");
        jobPostingDTO.setCompany("New Company");
        jobPostingDTO.setSalaryRange("New Range");
        jobPostingDTO.setRequiredSkills(List.of("New Skill"));
        jobPostingDTO.setApplicationDeadline("2024-10-01");

        when(jobPostingRepository.findById(id)).thenReturn(Optional.of(existingJobPosting));
        when(jobPostingRepository.saveAndFlush(any(JobPosting.class))).thenReturn(existingJobPosting);

        JobPostingResponse response = jobPostingService.editJobPosting(id, jobPostingDTO);

        assertEquals(200, response.getStatus());
        assertEquals("edit has been made", response.getMessage());
        verify(jobPostingRepository).saveAndFlush(existingJobPosting);

        assertEquals("New Title", existingJobPosting.getTitle());
        assertEquals("New Description", existingJobPosting.getDescription());
        assertEquals("New Company", existingJobPosting.getCompany());
        assertEquals("New Range", existingJobPosting.getSalaryRange());
        assertEquals(List.of("New Skill"), existingJobPosting.getRequiredSkills());
        assertEquals(LocalDate.parse("2024-10-01"), existingJobPosting.getApplicationDeadline());
    }

    @Test
    void getJobPosting_ShouldReturnFoundResponse_WhenJobPostingExists() {
        long id = 1L;
        JobPosting jobPosting = JobPosting.builder().id(id).title("Title").build();

        when(jobPostingRepository.findById(id)).thenReturn(Optional.of(jobPosting));

        JobPostingResponse response = jobPostingService.getJobPosting(id);

        assertEquals(200, response.getStatus());
        assertEquals("Job posting found", response.getMessage());
        assertEquals(jobPosting, response.getJobPosting());
    }

    @Test
    void getJobPosting_ShouldReturnNotFoundResponse_WhenJobPostingDoesNotExist() {
        long id = 1L;

        when(jobPostingRepository.findById(id)).thenReturn(Optional.empty());

        JobPostingResponse response = jobPostingService.getJobPosting(id);

        assertEquals(404, response.getStatus());
        assertEquals("Not found", response.getMessage());
    }

    @Test
    void deleteJobPosting_ShouldReturnDeletedResponse_WhenJobPostingExists() {
        long id = 1L;
        JobPosting jobPosting = JobPosting.builder().id(id).build();

        when(jobPostingRepository.findById(id)).thenReturn(Optional.of(jobPosting));

        JobPostingResponse response = jobPostingService.deleteJobPostingResponse(id);

        assertEquals(200, response.getStatus());
        assertEquals("Job posting has been deleted", response.getMessage());
        verify(jobPostingRepository).deleteById(id);
    }

    @Test
    void deleteJobPosting_ShouldReturnNotFoundResponse_WhenJobPostingDoesNotExist() {
        long id = 1L;

        when(jobPostingRepository.findById(id)).thenReturn(Optional.empty());

        JobPostingResponse response = jobPostingService.deleteJobPostingResponse(id);

        assertEquals(404, response.getStatus());
        assertEquals("Not found", response.getMessage());
    }

    @Test
    void createJobPosting_ShouldReturnCreatedResponse_WhenValidDataIsProvided() {
        JobPostingDTO jobPostingDTO = new JobPostingDTO();
        jobPostingDTO.setTitle("Java Developer");
        jobPostingDTO.setDescription("Java Developer needed");
        jobPostingDTO.setCompany("Tech Company");
        jobPostingDTO.setSalaryRange("60k-80k");
        jobPostingDTO.setRequiredSkills(List.of("Java", "Spring Boot"));
        jobPostingDTO.setApplicationDeadline("2024-09-01");

        JobPosting jobPosting = JobPosting.builder()
                .title(jobPostingDTO.getTitle())
                .description(jobPostingDTO.getDescription())
                .company(jobPostingDTO.getCompany())
                .salaryRange(jobPostingDTO.getSalaryRange())
                .requiredSkills(jobPostingDTO.getRequiredSkills())
                .applicationDeadline(LocalDate.parse(jobPostingDTO.getApplicationDeadline()))
                .build();

        when(jobPostingRepository.save(any(JobPosting.class))).thenReturn(jobPosting);

        JobPostingResponse response = jobPostingService.createJobPosting(jobPostingDTO);

        assertEquals(201, response.getStatus());
        assertEquals("Job Posting has been created", response.getMessage());
        verify(jobPostingRepository).save(any(JobPosting.class));
    }
}