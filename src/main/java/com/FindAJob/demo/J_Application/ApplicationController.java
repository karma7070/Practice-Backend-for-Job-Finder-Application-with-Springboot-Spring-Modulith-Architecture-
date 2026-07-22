package com.FindAJob.demo.J_Application;

import com.FindAJob.demo.J_Application.internal.ApplicationService;
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

}
