package com.FindAJob.demo.refreshtoken;

import com.FindAJob.demo.SecurityPackage.AuthDTO;
import com.FindAJob.demo.SecurityPackage.AuthResDTO;
import com.FindAJob.demo.refreshtoken.internal.RefreshService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/app/refresh")
public class RefreshController {

    private final RefreshService servRef;


    public RefreshController(RefreshService servRef) {
        this.servRef = servRef;
    }

    @PostMapping(path = "/refAcc")
    public AuthResDTO refreshAccessToken(@RequestBody AuthDTO auth){

      return  servRef.refreshAccessToken(auth.email());

    }

}
