package com.FindAJob.demo.jobs;

public record SearchReqDTO(String jobTitle,
                           JobFields field,
                           String description,
                           JobAvailability avail) {

}
