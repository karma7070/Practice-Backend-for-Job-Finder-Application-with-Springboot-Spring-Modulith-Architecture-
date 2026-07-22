package com.FindAJob.demo.jobs;

public record JobCreatedEvent(Long id,
                              String title,
                              String email) {
}
