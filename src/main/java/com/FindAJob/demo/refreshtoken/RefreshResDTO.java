package com.FindAJob.demo.refreshtoken;

import com.FindAJob.demo.reg_users.Reg_Users;

public record RefreshResDTO(String email,
                            String token,
                            String refToken

) {
/*
    public static RefreshResDTO from(RefreshToken refreshT, Reg_Users user){
        return new RefreshResDTO(
                user.getEmail(),
                token,
                refreshT.getToken()

        );

    }
*/
}
