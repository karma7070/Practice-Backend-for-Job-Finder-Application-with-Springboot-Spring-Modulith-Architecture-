package com.FindAJob.demo.reg_users;

import com.FindAJob.demo.J_Application.ApplicationMadeEvent;
import com.FindAJob.demo.jobs.JobCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    @ApplicationModuleListener
    public void on(JobCreatedEvent event){
        System.out.println("New Job Available");
        String email = event.email();
    }

    @ApplicationModuleListener
    public void on(ApplicationMadeEvent event){
        System.out.println("Applied successfully");
    }
}
