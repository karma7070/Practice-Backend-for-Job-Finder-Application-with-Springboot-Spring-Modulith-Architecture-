package com.FindAJob.demo.SecurityPackage;


import com.FindAJob.demo.companies.CompService;
import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.internal.Reg_UsersService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final Reg_UsersService userSrv;
    private final CompService compSrv;


    public CustomerUserDetailsService(Reg_UsersService userSrv, CompService compSrv) {
        this.userSrv = userSrv;
        this.compSrv = compSrv;
    }


    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username)
            throws UsernameNotFoundException {

         Optional<Reg_Users> user =
                 userSrv.getUserByEmail(username);
         if(user.isPresent()){
             return user.get();
         }

         Optional<Companies> company =
                 compSrv.getUserByEmail(username);
         if(company.isPresent()){
             return company.get();
         }

         throw new UsernameNotFoundException("No account exists with this email!");

    }
}
