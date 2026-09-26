package com.FindAJob.demo.jobs.events;

public record JobCreatedEvent(Long id,
                              String title,
                              String email) {
}
