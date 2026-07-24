package com.FindAJob.demo.J_Application;

import com.FindAJob.demo.jobs.Jobs;
import com.FindAJob.demo.reg_users.Reg_Users;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apnId;

    @ManyToOne
    private Jobs job;

    @ManyToOne
    private Reg_Users user;

    @Column(nullable = false)
    private String info;
    private Instant applied_at;
    private AppStatus status;

    public Application(Jobs job, Reg_Users user, String info, Instant applied_at, AppStatus status){
        this.job = job;
        this.user = user;
        this.info = info;
        this.applied_at = Instant.now();
        this.status = AppStatus.PENDING;
    }

    public Application(){

    }

    public Long getId() {
        return apnId;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public Reg_Users getUser() {
        return user;
    }

    public void setUser(Reg_Users user) {
        this.user = user;
    }

    public String getInfo() {
        return info;
    }


    public void setInfo(String info) {
        this.info = info;
    }

    public Instant getApplied_at() {
        return applied_at;
    }

    public AppStatus getStatus() {
        return status;
    }

    public void setStatus(AppStatus status){
        this.status = status;
    }

}
