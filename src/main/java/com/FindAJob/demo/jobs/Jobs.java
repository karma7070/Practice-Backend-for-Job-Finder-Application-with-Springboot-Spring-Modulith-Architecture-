package com.FindAJob.demo.jobs;


import com.FindAJob.demo.companies.Companies;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String job_title;
    private String description;
    private Double salary;
    private JobFields field;
    private JobAvailability availability;
    private Instant posted_at;
    private String posted_by;


    @ManyToOne
    private Companies company;

    public Jobs(String job_title,
                String description,
                Double salary,
                JobFields field,
                JobAvailability availability,
                Instant posted_at,
                String posted_by,
                Companies company){
        this.job_title = job_title;
        this.description = description;
        this.salary = salary;
        this.field = field;
        this.availability = availability;
        this.posted_at = Instant.now();
        this.posted_by= posted_by;
        this.company = company;
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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

    public Companies getCompany() {
        return company;
    }
}
