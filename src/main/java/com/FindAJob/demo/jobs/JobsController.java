package com.FindAJob.demo.jobs;

import com.FindAJob.demo.jobs.internal.JobsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/app/jobs")
public class JobsController {

  private final JobsService service;

  public JobsController(JobsService service){

    this.service = service;
  }

// Job creation endpoint\


  @GetMapping(path = "/all")
    public List<JobResponseDTO> getAllJobs(){

    return service.getAllJobs();
  }

  @GetMapping(path = "/one/{id}")
    public JobResponseDTO getAJob(@PathVariable Long id){

    return service.getAJob(id);
  }

  @GetMapping(path = "/jobsbycompany")
  public List<JobResponseDTO> getJobByComp(){

    return service.getJobsByCompany();
  }

  @PostMapping(path = "/create")
    public JobResponseDTO createAJob(@RequestBody JobRequestDTO request){

    return service.addJob(request);
  }

  @PatchMapping(path = "/update")
    public JobResponseDTO updateJobDetails(@RequestBody JobRequestDTO request, Long id){

    return service.updateJob(request, id);
  }

  @PatchMapping(path = "/setAvailStatus/{id}")
  public JobResponseDTO setAvailability(@PathVariable Long id,
                                        @RequestBody JobAvailReqDTO req){

    return service.setAvailabilityStatus(id, req);
  }

  @DeleteMapping(path = "/delete/{id}")
  public JobResponseDTO deleteJob(@PathVariable Long id){

    return service.deleteJob(id);
  }

}
