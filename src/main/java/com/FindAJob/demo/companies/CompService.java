package com.FindAJob.demo.companies;

import com.FindAJob.demo.companies.internal.CompRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompService {

    private final CompRepository serv_repository;


    public CompService(CompRepository serv_repository){
        this.serv_repository = serv_repository;
    }

    // ////ADD A COMPANY

    public CompResponseDTO addCompany(CompRequestDTO request){
        Companies company1 = this.RequestToComp(request);

        serv_repository.save(company1);

        CompResponseDTO resp1 = CompResponseDTO.from(company1);

        return resp1;
    }

    //Update Company details

    public CompResponseDTO updateCompany(CompRequestDTO request, Long id){
        Optional<Companies> opt_comp1 = serv_repository.findById(id);

        if(opt_comp1.isEmpty()){
            throw new RuntimeException("Company doesn't exist!!");
        }

        Companies comp2 = this.checkAndReturn(request, opt_comp1.get());

        serv_repository.save(comp2);

        return CompResponseDTO.from(comp2);
    }










                ////////////////////////////
    /////////////  Service functions    /////////////////////////////////
                ///////////////////////////

    //Converting requestDTO to company object
    public Companies RequestToComp(CompRequestDTO request){
        Companies comp1 = new Companies(
                request.comp_name(),
                request.location(),
                request.comp_email()
        );

        return comp1;
    }

    //Checking if a request is empty before updating

    public Companies checkAndReturn(CompRequestDTO request, Companies company){

        if(request.comp_name() != null && !(request.comp_name().isBlank())){
            company.setComp_name(request.comp_name());
        }

        if(request.location() != null && !(request.location().isBlank())){
            company.setLocation(request.location());
        }

        if(request.comp_email() != null && !(request.comp_email().isBlank())){
            company.setComp_email(request.comp_email());
        }

        return company;
    }

    //Job uses this to get company

    public Companies getCompId(Long id){
       Optional <Companies> comp = serv_repository.findById(id);

       if(comp.isPresent()) {
           return comp.get();
       } else
           return null;
    }

}



