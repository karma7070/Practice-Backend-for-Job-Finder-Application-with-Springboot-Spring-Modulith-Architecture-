package com.FindAJob.demo.companies;


import com.FindAJob.demo.jobs.JobCreatedEvent;
import com.FindAJob.demo.jobs.JobDeletedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.concurrent.CompletableFuture;

@Component
public class CompanyListener {

    @ApplicationModuleListener
    public CompletableFuture<PrintStream> on(JobCreatedEvent event){
        PrintStream statement = System.out.printf(" You created job with ID %d and title %s", event.id(), event.title());
        return CompletableFuture.completedFuture(statement);
    }

    @ApplicationModuleListener
    public void on(JobDeletedEvent event){
        String msg = "Job DELETED";
        Long id = event.id();

        System.out.println(msg);
        System.out.println(id);
    }

}
