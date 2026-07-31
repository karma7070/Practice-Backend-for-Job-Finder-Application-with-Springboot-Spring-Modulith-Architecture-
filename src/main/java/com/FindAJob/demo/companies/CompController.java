package com.FindAJob.demo.companies;

import com.FindAJob.demo.SecurityPackage.AuthDTO;
import com.FindAJob.demo.SecurityPackage.AuthResDTO;
import com.FindAJob.demo.companies.internal.CompService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/app/comp")
public class CompController {

    private final CompService service;

    public CompController(CompService service){
        this.service = service;

    }

    @PostMapping(path = "/create")
    public CompResponseDTO createCompany(@RequestBody CompRequestDTO request){

        return service.addCompany(request);
    }

    @PostMapping(path = "/logIn")
    public AuthResDTO logIn(@RequestBody AuthDTO auth){

        return service.logIn(auth);
    }
    @PatchMapping(path = "/update/{id}")
    public CompResponseDTO updateCompany(@RequestBody CompRequestDTO request,
                                         @PathVariable Long id){

        return service.updateCompany(request, id);
    }


}
