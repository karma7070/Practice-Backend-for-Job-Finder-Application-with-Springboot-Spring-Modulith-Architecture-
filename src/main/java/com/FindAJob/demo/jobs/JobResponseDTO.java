package com.FindAJob.demo.jobs;

import java.time.Instant;

public record JobResponseDTO (
        String job_title,
        String description,
        JobFields field,
        JobAvailability availability,
        Instant posted_at,
        String posted_by){

    public static JobResponseDTO from(Jobs job){
        return new JobResponseDTO(job.getJob_title(),
                job.getDescription(),
                job.getField(),
                job.getAvailability(),
                job.getPosted_at(),
                job.getPosted_by());

    }

}
