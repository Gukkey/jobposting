package com.gukkey.jobposting.model.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostingDTO {
    String title;

    String description;

    String company;

    String salaryRange;

    List<String> requiredSkills;

    String applicationDeadline;
}
