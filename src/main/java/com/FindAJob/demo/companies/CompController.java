package com.FindAJob.demo.companies;

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

    @PatchMapping(path = "/update/{id}")
    public CompResponseDTO updateCompany(@RequestBody CompRequestDTO request, @PathVariable Long id){
        return service.updateCompany(request, id);
    }


}
