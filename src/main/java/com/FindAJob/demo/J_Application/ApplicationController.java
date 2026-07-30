package com.FindAJob.demo.J_Application;

import com.FindAJob.demo.J_Application.internal.ApplicationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/app/application")
public class ApplicationController {

    private final ApplicationService service;


    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping(path = "/post")
    public ApplicationResDTO Apply(@RequestBody ApplicationReqDTO request){
      return  service.createApn(request);
    }

    @GetMapping(path = "/get")
    public List<ApplicationResDTO> getApns(){
        return service.getApn();
    }
//company sets status i.e approves or denies or...

    @GetMapping(path = "/view_applications")
        public List<ApplicationResDTO> getAppByEmail(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppByEmailReqDTO accEmail = new AppByEmailReqDTO(email);
         return service.getApnByEmail(accEmail);
    }

    @PatchMapping(path = "/company_assesses/{id}")
    public ApplicationResDTO setStatus(@RequestBody AppStatusDTO appStatus, @PathVariable Long id){
        return service.setStatus(appStatus, id);
    }

}
