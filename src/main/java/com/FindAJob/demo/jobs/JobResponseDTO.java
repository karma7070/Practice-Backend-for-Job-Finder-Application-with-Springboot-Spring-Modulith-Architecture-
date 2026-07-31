package com.FindAJob.demo.jobs;

import com.FindAJob.demo.companies.Companies;

import java.time.Instant;

public record JobResponseDTO (
        String job_title,
        String description,
        Double salary,
        JobFields field,
        JobAvailability availability,
        Instant posted_at,
        String posted_by,
        Long compId){

    public static JobResponseDTO from(Jobs job){

        Companies company = job.getCompany();

        return new JobResponseDTO(job.getJob_title(),
                job.getDescription(),
                job.getSalary(),
                job.getField(),
                job.getAvailability(),
                job.getPosted_at(),
                company.getComp_name(),
                company.getId()
            );

    }

}
