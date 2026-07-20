package com.FindAJob.demo.reg_users.internal;


import com.FindAJob.demo.reg_users.Reg_UserRequestDTO;
import com.FindAJob.demo.reg_users.Reg_UserResponseDTO;
import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Reg_UsersService {

    private final Reg_UsersRepository userRepository;
    private final Reg_UsersService userService;

    public Reg_UsersService(Reg_UsersRepository userRepository, Reg_UsersService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }


// ////ADD A USER

    public Reg_UserResponseDTO CreateUser(Reg_UserRequestDTO request){

        Reg_Users user1 = userService.RequestToUser(request);

        userRepository.save(user1);

        Reg_UserResponseDTO resp1 = Reg_UserResponseDTO.from(user1);

        return resp1;
    }

//Update User details

    public Reg_UserResponseDTO updateCompany(Reg_UserRequestDTO request, Long id){

        Optional<Reg_Users> opt_user1 = userRepository.findById(id);

        if(opt_user1.isEmpty()){
            throw new RuntimeException("Company doesn't exist!!");
        }

        Reg_Users user2 = userService.checkAndReturn(request, opt_user1.get());

        return Reg_UserResponseDTO.from(user2);
    }











    ////////////////////////////
    /////////////  Service functions    /////////////////////////////////
    ///////////////////////////

//Converting requestDTO to company object
    public Reg_Users RequestToUser(Reg_UserRequestDTO request){
        Reg_Users user = new Reg_Users(
                request.name(),
                request.age(),
                request.gender(),
                request.profession(),
                request.email()
        );

        return user;
    }

//Checking if a request is empty before updating

    public Reg_Users checkAndReturn(Reg_UserRequestDTO request, Reg_Users user){

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
            user.setGender(request.gender());
        }

        if(request.email() != null && !(request.email().isBlank())){
            user.setEmail(request.email());
        }

        return user;
    }

}


