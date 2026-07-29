package com.FindAJob.demo.SecurityPackage;

import com.FindAJob.demo.reg_users.Reg_Users;
import com.FindAJob.demo.reg_users.internal.Reg_UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JWTService jwtSvc;
    private final Reg_UsersRepository userRepo;


    public AuthFilter(JWTService jwtSvc, Reg_UsersRepository userRepo) {
        this.jwtSvc = jwtSvc;
        this.userRepo = userRepo;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
if(SecurityContextHolder.getContext().getAuthentication() == null) {

//HTTP request comes with a header which contains the string as seen below, now that string the variable 'authHeader'

            String authHeader = request.getHeader("Authorization");

//checks if authHeader is null or doesn't start with bearer meaning no token

            if (authHeader == null
                    || !(authHeader.startsWith("Bearer "))) {

                filterChain.doFilter(request, response);

                return;
            }

//token removed from string by creating a substring
// from the 7th element in the string to the end, i.e "Bearer kwpmpidjpokklm",
//is in the 7th slot so a new string is created from that 7th element

            String token = authHeader.substring(7);

//the method for checking token validity is called in an if statement since it returns
// boolean and if token is invalid the response below is sent (401 for unauthorized)

            if (!jwtSvc.isTokenValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or Expired Token");
                return;
            }
//Email is extracted using the extractEmail method from the JWTService file

            String email = jwtSvc.extractEmail(token);

            Reg_Users user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));


//Gets the user and checks for its role and things it's allowed to do under said role
            //Represents a logged in user
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

//Security context holder holds
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);

        }

        filterChain.doFilter(request, response);

    }

}