package com.FindAJob.demo.reg_users.internal;


import com.FindAJob.demo.SecurityPackage.AuthDTO;
import com.FindAJob.demo.SecurityPackage.AuthResDTO;
import com.FindAJob.demo.SecurityPackage.JWTService;
import com.FindAJob.demo.reg_users.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class Reg_UsersService {

    private final Reg_UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwt;

    public Reg_UsersService(Reg_UsersRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwt){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }


// ////ADD A USER

    public Reg_UserResponseDTO CreateUser(Reg_UserRequestDTO request){

        Reg_Users user1 = this.createCheck(request);

        userRepository.save(user1);

        Reg_UserResponseDTO resp1 = Reg_UserResponseDTO.from(user1);

        return resp1;
    }

//User logs in
    public AuthResDTO logIn(AuthDTO auth) {

        Reg_Users user = userRepository.findByEmail(auth.email())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));

        //this encodes the entered password with same key and compares to the stored one
        if (passwordEncoder.matches(auth.password(), user.getPassword())) {

            String token = jwt.generateToken(user.getEmail());

            return new AuthResDTO(jwt.extractEmail(token),
                                 token);
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

//Update User info

    public Reg_UserResponseDTO updateUser(Reg_UserRequestDTO request, Long id){
        //add empty request exception handler

        Optional<Reg_Users> opt_user1 = userRepository.findById(id);

        if(opt_user1.isEmpty()){
            throw new UsernameNotFoundException("User doesn't exist!!");
        }

        Reg_Users user2 = this.updateCheck(request, opt_user1.get());

        userRepository.save(user2);

        return Reg_UserResponseDTO.from(user2);
    }
//Delete user

    public Reg_UserResponseDTO deleteUser(Long id){
       Reg_Users user = userRepository.findById(id)
           .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));


        Reg_UserResponseDTO response = Reg_UserResponseDTO.from(user);

        userRepository.deleteById(id);

        return response;
    }











               ////////////////////////////
    /////////////  Service functions    /////////////////////////////////
               ///////////////////////////

//Converting requestDTO to company object
    public Reg_Users RequestToUser(Reg_UserRequestDTO request){

        String passW = passwordEncoder.encode(request.password());

        Reg_Users user = new Reg_Users(
                request.name(),
                request.age(),
                request.gender(),
                request.profession(),
                request.email(),
                passW,
                request.role()
        );

        return user;
    }

//Checking user request before creating user account and verifying password as well

    public boolean confirmPswrd(String pass, String cfPass){

            if(Objects.equals(pass, cfPass)){//used to compare if strings are equal
                return true;
            } else {
                return false;
            }
    }


    public Reg_Users createCheck(Reg_UserRequestDTO request) {

        if ((request.name() != null && !(request.name().isBlank()))
                && (request.age() != null)
                && (request.gender() != null)
                && (request.profession() != null && !(request.profession().isBlank()))
                && ((request.password() != null) && !(request.password().isBlank()))
                && ((request.confPassword() != null) && !(request.confPassword().isBlank()))
                && (request.email() != null && !(request.email().isBlank()))
                && (request.role() != null)) {

            if (confirmPswrd(request.password(), request.confPassword())) {

                Reg_Users user = this.RequestToUser(request);

                return user;

            } else {
                throw new RuntimeException("Passwords don't match");
            }


        } else {
            throw new RuntimeException("Fill all fields!");
        }
    }


//Checking if a request is empty before updating

    public Reg_Users updateCheck(Reg_UserRequestDTO request, Reg_Users user){

        if(request.name() != null && !(request.name().isBlank())){
            user.setName(request.name());
        }

        if(request.age() != null){
            user.setAge(request.age());
        }

        if(request.gender() != null){
            user.setGender(request.gender());
        }

        if(request.profession() != null && !(request.profession().isBlank())){
            user.setProfession(request.profession());
        }

        if(request.email() != null && !(request.email().isBlank())){
            user.setEmail(request.email());
        }

        return user;
    }

    public Reg_Users getUser(Long id){
        return userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not Found"));
    }

    public Optional<Reg_Users> getUserByEmail(String email){
        return userRepository
                .findByEmail(email);
    }



}



