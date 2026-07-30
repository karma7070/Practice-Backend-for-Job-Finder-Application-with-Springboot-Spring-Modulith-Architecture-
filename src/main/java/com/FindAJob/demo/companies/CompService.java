package com.FindAJob.demo.companies;

import com.FindAJob.demo.SecurityPackage.AuthDTO;
import com.FindAJob.demo.SecurityPackage.JWTService;
import com.FindAJob.demo.companies.internal.CompRepository;
import com.FindAJob.demo.SecurityPackage.AuthResDTO;
import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class CompService {

    private final CompRepository serv_repository;
    private final PasswordEncoder passWE;
    private final JWTService jwt;


    public CompService(CompRepository serv_repository, PasswordEncoder passWE, JWTService jwt){
        this.serv_repository = serv_repository;
        this.passWE = passWE;
        this.jwt = jwt;
    }

    // ////ADD A COMPANY

    public CompResponseDTO addCompany(CompRequestDTO request) {

        if (createCheck(request)) {

            Companies company1 = this.RequestToComp(request);

            serv_repository.save(company1);

            CompResponseDTO resp1 = CompResponseDTO.from(company1);

            return resp1;
        } else {
            throw new RuntimeException("Fill all Fields");
        }
    }
//Company logs in
    public AuthResDTO logIn(AuthDTO auth){
        Companies company = serv_repository.findByCompEmail(auth.email())
                .orElseThrow(()-> new UsernameNotFoundException("Company not found!"));

        if(passWE.matches(auth.password(), company.getPassword())){

            String token = jwt.generateToken(auth.email());

            return new AuthResDTO(jwt.extractEmail(token),
                    token);
        } else {

            throw new RuntimeException("Invalid Credentials");

        }
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
//Checks for null request before creating user
    public boolean createCheck(CompRequestDTO request){
        if((request.comp_name() != null && !(request.comp_name().isBlank()))
             && ((request.location() != null) && !(request.location().isBlank()))
             && ((request.comp_email() != null) && !(request.comp_email().isBlank()))
             && ((request.password() != null) && !(request.password().isBlank()))
                && ((request.confPass() != null) && !(request.confPass().isBlank()))
                && (request.role() != null)){

            return true;

        } else {
            return false;
        }
    }

    //Converting requestDTO to company object
    public Companies RequestToComp(CompRequestDTO request){

        if(Objects.equals(request.confPass(), request.password())){

            String passW = passWE.encode(request.password());


            Companies comp1 = new Companies(
                    request.comp_name(),
                    request.location(),
                    request.comp_email(),
                    passW,
                    request.role()
            );

            return comp1;
        } else {
            throw new RuntimeException("Re-Enter Passwords!");
        }

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
            company.setCompEmail(request.comp_email());
        }

        return company;
    }

    //Job uses this to get company

    public Companies getCompId(Long id){
       Optional <Companies> comp = Optional.of(serv_repository.findById(id)
               .orElseThrow(()-> new UsernameNotFoundException("User does not exist")));

       if(comp.isPresent()) {
           return comp.get();
       } else
           return null;
    }

    public Optional<Companies> getUserByEmail(String email){
        return serv_repository.
                findByCompEmail(email);
    }



}



