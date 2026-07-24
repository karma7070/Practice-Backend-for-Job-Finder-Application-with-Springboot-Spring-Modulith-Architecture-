package com.FindAJob.demo.J_Application.internal;


import com.FindAJob.demo.J_Application.*;
import com.FindAJob.demo.jobs.Jobs;
import com.FindAJob.demo.jobs.JobsService;
import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.Reg_UsersService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        ApplicationReqDTO request2 = this.checkForEmptyReq(request);

        Jobs job = jService.getJob(request2.jobId());

        Reg_Users user = uService.getUser(request2.userId());

        Application app1 = new Application(job,
                                            user,
                                            request2.info(),
                                            Instant.now(),
                                            AppStatus.PENDING);
                                            
        repository.save(app1);

        ApplicationMadeEvent event = new ApplicationMadeEvent(app1.getId(), app1.getUser().getId(), app1.getInfo());

        publisher.publishEvent(event);

        ApplicationResDTO response = ApplicationResDTO.from(app1);

        return response;
    }

//Company approves application
    public ApplicationResDTO setStatus(AppStatusDTO appStatus, Long id){

        Optional<Application> apn = repository.findById(id);
        if(apn.isEmpty()){
            throw new RuntimeException("Application doesn't exist");
        }

        if(appStatus.status() == AppStatus.APPROVED) {
            apn.get().setStatus(AppStatus.APPROVED);
        }
          else if(appStatus.status() == AppStatus.DENIED){
              apn.get().setStatus(AppStatus.DENIED);
        } else {
              apn.get().setStatus(AppStatus.PENDING);
        }
          repository.save(apn.get());

     return ApplicationResDTO.from(apn.get());
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

    public ApplicationReqDTO checkForEmptyReq(ApplicationReqDTO request){

       if(request.jobId() == null
               || request.userId() == null
               || request.info() == null
               ){
         throw new RuntimeException("Request has an Empty value");
       }

       return request;
    }
}
