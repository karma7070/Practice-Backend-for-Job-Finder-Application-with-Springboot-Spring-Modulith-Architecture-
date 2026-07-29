package com.FindAJob.demo.SecurityPackage;


import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.internal.Reg_UsersService;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final Reg_UsersService userSrv;


    public CustomerUserDetailsService(Reg_UsersService userSrv) {
        this.userSrv = userSrv;
    }


    @Override
    public UserDetails loadUserByUsername(@NonNull String username)
            throws UsernameNotFoundException {

        Reg_Users user = userSrv.getUserByEmail(username);

        return user;
    }
}
