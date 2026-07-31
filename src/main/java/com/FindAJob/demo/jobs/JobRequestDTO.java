package com.FindAJob.demo.jobs;

import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.companies.internal.CompService;

import java.time.Instant;
import java.util.Optional;

public record JobRequestDTO (
        String job_title,
        String description,
        Double salary,
        JobFields field,
        JobAvailability availability,
        Long compId) {

}
