package com.FindAJob.demo.reg_users;

import com.FindAJob.demo.reg_users.internal.Reg_UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/app/users")
public class Reg_UserController {

    private final Reg_UsersService service;

    public Reg_UserController(Reg_UsersService service) {
        this.service = service;
    }

    @PostMapping(path = "/create")
    public Reg_UserResponseDTO createUser(@RequestBody Reg_UserRequestDTO request){
        return service.CreateUser(request);
    }

    @PostMapping(path = "/update/{id}")
    public Reg_UserResponseDTO updateUser(@RequestBody Reg_UserRequestDTO request, @PathVariable Long id){
        return service.updateUser(request, id);
    }

    @DeleteMapping (path = "/delete/{id}")
    public Reg_UserResponseDTO deleteUser(@PathVariable Long id){
        return service.deleteUser(id);
    }
}
