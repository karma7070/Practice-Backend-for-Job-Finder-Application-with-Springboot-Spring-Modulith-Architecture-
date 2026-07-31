package com.FindAJob.demo.J_Application.internal;


import com.FindAJob.demo.J_Application.*;
import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.jobs.Jobs;
import com.FindAJob.demo.jobs.internal.JobsService;
import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.internal.Reg_UsersService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.InvalidClassException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    //get applications for ADMIN

    public List<ApplicationResDTO> getApn(){
        List<Application> apns = repository.findAll();

        List<ApplicationResDTO> response = this.getResDTO(apns);

        return response;
    }



//get specific applications by email for USERS

    public List<ApplicationResDTO> getApnByEmail(){

        //  if(email.email() != auth.email)
//finds applications based on user emails

        String email = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

        List<Application> applications =
                repository.findByUserEmail(email);

        List<ApplicationResDTO> responses = new ArrayList<>();

        if(!(applications.isEmpty())){

            for(int i = 0; i<applications.size(); i++){

                Application appl = applications.get(i);

                responses.add(ApplicationResDTO.from(appl));

            }
            return responses;

        } else {

            throw new UsernameNotFoundException("User has no applications");
        }

    }

//create application for USERS

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

        ApplicationMadeEvent event = new ApplicationMadeEvent(app1.getId(),
                                                                app1.getUser().getId(),
                                                                app1.getInfo());

        publisher.publishEvent(event);

        ApplicationResDTO response = ApplicationResDTO.from(app1);

        return response;
    }

//Company gets applications (for Companies)

    public List<ApplicationResDTO> getByCompany(){

        List<Application> appns = repository.findAll();

        String email = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();

       ArrayList <ApplicationResDTO> responseArray = new ArrayList<>();

        for(int i = 0; i<appns.size(); i++){

            Application app = appns.get(i);

            if(Objects.equals(app.getJob().getCompany().getCompEmail(), email)){

                responseArray.add(ApplicationResDTO.from(app));

            } else {
                throw new UsernameNotFoundException("Email doesn't match");
            }

        }
        if(!(responseArray.isEmpty())){

            return responseArray;

        } else{

            throw new UsernameNotFoundException("No user applied yet");
        }

    }

//Company approves/denies application (for Companies)

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
