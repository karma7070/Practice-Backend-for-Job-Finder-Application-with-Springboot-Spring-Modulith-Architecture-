package com.FindAJob.demo.jobs;

import com.FindAJob.demo.jobs.internal.JobsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class JobsController {

  private final JobsService service;

  public JobsController(JobsService service){
      this.service = service;
  }

// Job creation endpoint


}
