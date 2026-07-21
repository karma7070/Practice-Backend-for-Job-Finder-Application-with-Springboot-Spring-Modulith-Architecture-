package com.FindAJob.demo.jobs;

import java.time.Instant;

public record JobRequestDTO (
        String job_title,
        String description,
        Double salary,
        JobFields field,
        JobAvailability availability,
        Instant posted_at,
        String posted_by,
        Long compId) {

}
