package com.FindAJob.demo.companies;

import com.FindAJob.demo.companies.internal.CompService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CompController {

    private final CompService service;

    public CompController(CompService service){
        this.service = service;

    }

    @PostMapping
    public CompResponseDTO createCompany(@RequestBody CompRequestDTO request){
        return service.addCompany(request);
    }

    @PatchMapping
    public CompResponseDTO updateCompany(@RequestBody CompRequestDTO request, Long id){
        return service.updateCompany(request, id);
    }


}
