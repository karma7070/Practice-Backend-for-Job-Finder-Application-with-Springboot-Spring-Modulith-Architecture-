package com.FindAJob.demo.companies;

import com.FindAJob.demo.SecurityPackage.UserRoles;

public record CompRequestDTO(
        String comp_name,
        String location,
        String comp_email,
        String password,
        String confPass,
        UserRoles role) {



}
