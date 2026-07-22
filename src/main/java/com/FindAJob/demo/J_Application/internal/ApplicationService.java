package com.FindAJob.demo.J_Application.internal;


import com.FindAJob.demo.J_Application.Application;
import com.FindAJob.demo.J_Application.ApplicationMadeEvent;
import com.FindAJob.demo.J_Application.ApplicationReqDTO;
import com.FindAJob.demo.J_Application.ApplicationResDTO;
import com.FindAJob.demo.jobs.Jobs;
import com.FindAJob.demo.jobs.JobsService;
import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.Reg_UsersService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final JobsService jService;
    private final Reg_UsersService uService;
    private final ApplicationEventPublisher publisher;

    public ApplicationService(ApplicationRepository repository, JobsService jService, Reg_UsersService uService, ApplicationEventPublisher publisher){
        this.repository = repository;
        this.jService = jService;
        this.uService = uService;
        this.publisher = publisher;
    }

    //get applications
    public List<ApplicationResDTO> getApn(){
        List<Application> apns = repository.findAll();

        List<ApplicationResDTO> response = this.getResDTO(apns);

        return response;
    }

    //create application

    public ApplicationResDTO createApn(ApplicationReqDTO request){

        Jobs job = jService.getJob(request.jobId());

        Reg_Users user = uService.getUser(request.userId());

        Application app1 = new Application(job,
                                            user,
                                            request.info(),
                                            request.applied_at(),
                                            request.status());
        repository.save(app1);

        ApplicationMadeEvent event = new ApplicationMadeEvent(app1.getId(), app1.getUser().getId(), app1.getInfo());

        publisher.publishEvent(event);

        ApplicationResDTO response = ApplicationResDTO.from(app1);

        return response;
    }

    //update application
                     /////////////////////////////
///////////////////////// Service Functions  ///////////////////////////
                      //////////////////////////

    public List<ApplicationResDTO> getResDTO(List<Application> apns){

       List<ApplicationResDTO> resps = new ArrayList<>();

       for(int i = 0; i<apns.size(); i++){
           Application apn = apns.get(i);

           resps.add(ApplicationResDTO.from(apn));
       }

       return resps;
    }
}
