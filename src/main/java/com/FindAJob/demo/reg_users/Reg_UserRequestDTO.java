package com.FindAJob.demo.reg_users;

import com.FindAJob.demo.SecurityPackage.UserRoles;

public record Reg_UserRequestDTO(
        String name,
        Integer age,
        Gen_Type gender,
        String profession,
        String password,
        String confPassword,
        String email,
        UserRoles role
) {
}
