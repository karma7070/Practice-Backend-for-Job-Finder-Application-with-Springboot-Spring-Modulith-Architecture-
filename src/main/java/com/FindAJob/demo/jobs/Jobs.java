package com.FindAJob.demo.jobs;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String job_title;
    private String description;
    private JobFields field;
    private JobAvailability availability;
    private Instant posted_at;
    private String posted_by;

    public Jobs(String job_title,
                String description,
                JobFields field,
                JobAvailability availability,
                Instant posted_at,
                String posted_by){
        this.job_title = job_title;
        this.description = description;
        this.field = field;
        this.availability = availability;
        this.posted_at = Instant.now();
        this.posted_by= posted_by;
    }

    public Jobs(){

    }

    public Long getId() {
        return id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobFields getField() {
        return field;
    }

    public void setField(JobFields field) {
        this.field = field;
    }

    public JobAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(JobAvailability availability) {
        this.availability = availability;
    }

    public Instant getPosted_at() {
        return posted_at;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

}
